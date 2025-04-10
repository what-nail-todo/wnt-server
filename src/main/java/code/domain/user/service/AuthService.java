package code.domain.user.service;

import code.domain.user.UserRepository;
import code.domain.user.dto.req.SignUpRequestDto;
import code.domain.user.entity.User;
import code.global.exception.entity.CustomErrorCode;
import code.global.exception.entity.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signUp(SignUpRequestDto signUpRequestDto){
        if(userRepository.existsByLoginId(signUpRequestDto.getLoginId()))
            throw new RestApiException(CustomErrorCode.DUPLICATED_LOGIN_ID);

        User user = User.builder()
                .signUpRequestDto(signUpRequestDto)
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .build();

        return userRepository.save(user).getLoginId();
    }

    @Transactional(readOnly = true)
    public Boolean checkIdDuplicated(String targetId){
        return !userRepository.existsByLoginId(targetId);
    }

}
