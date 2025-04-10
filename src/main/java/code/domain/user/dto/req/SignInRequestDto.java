package code.domain.user.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequestDto {

    @Size(max = 50, message = "이메일 길이 제한을 초과하였습니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;

    @Size(max = 100, message = "비밀번호 길이 제한을 초과하였습니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])(?=.*[a-z]).{8,15}$", message = "비밀번호 구성 조건을 충족하지 않습니다.")
    private String password;
}
