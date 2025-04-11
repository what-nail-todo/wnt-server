package code.domain.user.controller;

import code.domain.common.dto.ResponseDto;
import code.domain.user.dto.req.SignInRequestDto;
import code.domain.user.dto.req.SignUpRequestDto;
import code.domain.user.service.AuthService;
import code.global.security.domain.TokenResponse;
import code.global.security.jwt.util.CookieUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${server.domain}")
    private String domain;

    private final AuthService authService;

    @PostMapping("/sign-up/normal")
    public ResponseEntity<ResponseDto<String>> normalSignUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(authService.normalSignUp(signUpRequestDto), "일반 회원가입 완료"));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseDto<String>> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto){
        TokenResponse tokenResponse = authService.signIn(signInRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, CookieUtil.createCookie("accessToken", tokenResponse.getAccessToken(), tokenResponse.getExpiredTime()).toString())
                .body(ResponseDto.of(signInRequestDto.getEmail(), "일반 로그인 완료"));
    }

    @GetMapping("/check-email")
    public ResponseEntity<ResponseDto<Boolean>> checkEmailDuplicated(@RequestParam String targetEmail){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(authService.checkEmailDuplicated(targetEmail), "아이디 중복 검사 완료"));
    }
}
