package code.domain.email.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "인증 메일 전송 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SendVerificationEmailRequestDto {

    @Schema(
            description = "사용자 이메일",
            example = "whatnailtodo@naver.com"
    )
    @Size(max = 50, message = "이메일 길이 제한을 초과하였습니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;
}
