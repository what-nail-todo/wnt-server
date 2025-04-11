package code.domain.email.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VerifyEmailAuthCodeRequestDto {

    @Size(max = 50, message = "이메일 길이 제한을 초과하였습니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;

    @Size(max = 6, message = "인증 코드는 6자리입니다.")
    @NotBlank(message = "인증코드가 비어있습니다.")
    private String inputCode;
}
