package code.domain.user.controller;

import code.domain.common.dto.ResponseDto;
import code.domain.email.dto.req.SendVerificationEmailRequestDto;
import code.domain.email.dto.req.VerifyEmailAuthCodeRequestDto;
import code.domain.user.dto.req.NormalSignUpRequestDto;
import code.domain.user.dto.req.SignInRequestDto;
import code.domain.user.dto.req.SocialSignUpRequestDto;
import code.domain.user.service.AuthService;
import code.global.security.domain.TokenResponse;
import code.global.security.jwt.util.CookieUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up/normal")
    public ResponseEntity<ResponseDto<String>> normalSignUp(@Valid @RequestBody NormalSignUpRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(authService.normalSignUp(request), "일반 회원가입 완료"));
    }

    @PostMapping("/sign-up/social")
    public ResponseEntity<ResponseDto<String>> socialSignUp(@Valid @RequestBody SocialSignUpRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(authService.socialSignUp(request), "소셜 회원가입 완료"));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseDto<String>> signIn(@Valid @RequestBody SignInRequestDto request){
        TokenResponse tokenResponse = authService.signIn(request);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, CookieUtil.createCookie("access-token", tokenResponse.getAccessToken(), tokenResponse.getExpiredTime()).toString())
                .body(ResponseDto.of(request.getEmail(), "일반 로그인 완료"));
    }

    @GetMapping("/email-duplicated")
    public ResponseEntity<ResponseDto<Boolean>> checkEmailDuplicated(@RequestParam String targetEmail){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(authService.checkEmailDuplicated(targetEmail), "아이디 중복 검사 완료"));
    }

    @PostMapping("/email")
    public ResponseEntity<ResponseDto<String>> sendVerificationEmail(@Valid @RequestBody SendVerificationEmailRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(authService.sendVerificationEmail(request), "인증메일 전송 완료"));
    }

    @PostMapping("/email/verification")
    public ResponseEntity<ResponseDto<Boolean>> verifyEmailAuthCode(@Valid @RequestBody VerifyEmailAuthCodeRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(authService.verifyEmailAuthCode(request), "메일 인증 완료"));
    }
}
