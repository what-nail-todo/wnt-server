package code.domain.user.service;

import code.domain.email.dto.req.SendVerificationEmailRequestDto;
import code.domain.email.dto.req.VerifyEmailAuthCodeRequestDto;
import code.domain.email.service.EmailService;
import code.domain.email.util.RandomCodeGenerator;
import code.domain.redis.service.RedisService;
import code.domain.user.repository.UserRepository;
import code.domain.user.dto.req.NormalSignUpRequestDto;
import code.domain.user.dto.req.SignInRequestDto;
import code.domain.user.dto.req.SocialSignUpRequestDto;
import code.domain.user.entity.User;
import code.global.exception.entity.CustomErrorCode;
import code.global.exception.entity.RestApiException;
import code.global.security.domain.TokenResponse;
import code.global.security.jwt.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmailService emailService;
    private final RedisService redisService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Transactional
    public String normalSignUp(NormalSignUpRequestDto request){
        if(userRepository.existsByEmail(request.getEmail()))
            throw new RestApiException(CustomErrorCode.DUPLICATED_EMAIL);

        if(!redisService.getEmailVerification(request.getEmail()))
            throw new RestApiException(CustomErrorCode.NOT_VERIFIED_EMAIL);

        User user = User.normalUserBuilder()
                .normalSignUpRequestDto(request)
                .password(passwordEncoder.encode(request.getPassword()))
                .buildNormalUser();

        log.info("[ socialSignUp() ] : 일반 회원가입 성공 \"email = {}\"", user.getEmail());

        return userRepository.save(user).getEmail();
    }

    @Transactional
    public String socialSignUp(SocialSignUpRequestDto request){
        Map<String, String> oauth2UserInfo = redisService.getOAuth2UserInfo(request.getProviderId());

        if(oauth2UserInfo.isEmpty())
            throw new RestApiException(CustomErrorCode.PROVIDER_ID_NOT_AVAILABLE);

        if(userRepository.existsByEmail(oauth2UserInfo.get("email")))
            throw new RestApiException(CustomErrorCode.DUPLICATED_EMAIL);

        User user = User.socialUserBuilder()
                .socialSignUpRequestDto(request)
                .email(oauth2UserInfo.get("email"))
                .password(UUID.randomUUID().toString())
                .provider(oauth2UserInfo.get("provider"))
                .buildSocialUser();

        log.info("[ socialSignUp() ] : 소셜 회원가입 성공 \"email = {}\"", user.getEmail());

        return userRepository.save(user).getEmail();
    }

    @Transactional(readOnly = true)
    public TokenResponse signIn(SignInRequestDto request){
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            log.info("[ signIn() ] : 유저 인증 성공 \"email = {}\"", authentication.getName());

            return jwtProvider.createToken(authentication.getName());
        }catch (UsernameNotFoundException e){
            throw new RestApiException(CustomErrorCode.USER_NOT_FOUND);
        }catch (BadCredentialsException e){
            throw new RestApiException(CustomErrorCode.PASSWORD_NOT_MATCHED);
        }
    }

    @Transactional(readOnly = true)
    public Boolean checkEmailDuplicated(String targetEmail){
        return !userRepository.existsByEmail(targetEmail);
    }

    public String sendVerificationEmail(SendVerificationEmailRequestDto request){

        if(userRepository.existsByEmail(request.getEmail()))
            throw new RestApiException(CustomErrorCode.DUPLICATED_EMAIL);

        String authCode = RandomCodeGenerator.generate();

        emailService.sendVerificationEmail(request.getEmail(), authCode);

        redisService.saveAuthCode(request.getEmail(), authCode);

        return request.getEmail();
    }

    public Boolean verifyEmailAuthCode(VerifyEmailAuthCodeRequestDto request){
        return redisService.verifyAuthCode(request.getEmail(), request.getInputCode());
    }

    @Transactional(readOnly = true)
    public boolean checkUserPresent(String email){
        return userRepository.existsByEmail(email);
    }
}
