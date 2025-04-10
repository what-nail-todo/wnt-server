package code.domain.user.controller;

import code.domain.common.dto.ResponseDto;
import code.domain.user.dto.req.SignUpRequestDto;
import code.domain.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto<String>> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(authService.signUp(signUpRequestDto), "회원가입 완료"));
    }

    @GetMapping("/check-id")
    public ResponseEntity<ResponseDto<Boolean>> checkIdDuplicated(@RequestParam String targetId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(authService.checkIdDuplicated(targetId), "아이디 중복 검사 완료"));
    }
}
