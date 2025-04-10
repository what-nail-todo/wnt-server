package code.domain.user.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequestDto {

    @Size(max = 50, message = "아이디 길이 제한을 초과하였습니다.")
    @NotBlank(message = "아이디가 비어있습니다.")
    private String loginId;

    @Size(max = 100, message = "비밀번호 길이 제한을 초과하였습니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])(?=.*[a-z]).{8,15}$", message = "비밀번호 구성 조건을 충족하지 않습니다.")
    private String password;

    @NotBlank(message = "이름이 비어있습니다.")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDay;

    @NotBlank(message = "프로필 이미지 키값이 비어있습니다")
    private String profileImageObjectKey;

    @NotBlank(message = "FcmToken이 비어있습니다.")
    private String fcmToken;

    // Custom Bean Validation 으로 바꿀 예정
    @NotBlank
    @Pattern(regexp = "^(customer|owner)$", message = "유효하지 않은 타입입니다.")
    private String userType;
}
