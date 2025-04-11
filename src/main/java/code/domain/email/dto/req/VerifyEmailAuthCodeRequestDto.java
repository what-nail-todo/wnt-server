package code.domain.email.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "메일 인증 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VerifyEmailAuthCodeRequestDto {

    @Schema(
            description = "사용자 이메일",
            example = "whatnailtodo@naver.com"
    )
    @Size(max = 50, message = "이메일 길이 제한을 초과하였습니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;

    @Schema(
            description = "인증 코드",
            example = "6zk12l"
    )
    @Size(max = 6, message = "인증 코드는 6자리입니다.")
    @NotBlank(message = "인증코드가 비어있습니다.")
    private String inputCode;
}
