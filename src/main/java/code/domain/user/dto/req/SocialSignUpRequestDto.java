package code.domain.user.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialSignUpRequestDto {

    @NotBlank(message = "providerId가 비어있습니다.")
    private String providerId;

    @NotBlank(message = "이름이 비어있습니다.")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDay;

    @NotBlank(message = "프로필 이미지 키값이 비어있습니다")
    private String profileImageObjectKey;

    @NotBlank(message = "FcmToken이 비어있습니다.")
    private String fcmToken;

    @NotBlank(message = "유저 타입이 비어있습니다.")
    @Pattern(regexp = "^(customer|owner)$", message = "유효하지 않은 타입입니다.")
    private String userType;

    @NotBlank(message = "광고성 메일 수신 여부가 비어있습니다.")
    private Boolean acceptAdEmails;
}
