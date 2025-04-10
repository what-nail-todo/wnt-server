package code.domain.user.service;

import code.domain.user.UserRepository;
import code.domain.user.dto.req.SignInRequestDto;
import code.domain.user.dto.req.SignUpRequestDto;
import code.domain.user.entity.Provider;
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

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Transactional
    public String normalSignUp(SignUpRequestDto signUpRequestDto){
        if(userRepository.existsByEmail(signUpRequestDto.getEmail()))
            throw new RestApiException(CustomErrorCode.DUPLICATED_LOGIN_ID);

        User user = User.builder()
                .signUpRequestDto(signUpRequestDto)
                .provider(Provider.NORMAL)
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .build();

        log.info("[normalSignUp] : Sign Up succeed with {}", user.getEmail());

        return userRepository.save(user).getEmail();
    }

    @Transactional(readOnly = true)
    public TokenResponse signIn(SignInRequestDto signInRequestDto){
        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInRequestDto.getEmail(), signInRequestDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            log.info("[signIn] : Authenticate succeed with {}", authentication.getName());

            return jwtProvider.createToken(authentication);
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

}
