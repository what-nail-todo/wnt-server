package code.domain.user.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialSignUpRequestDto {

    @Schema(
            description = "Provider Id",
            example = "asdjhfgqhbfkm-adsfbkjhbeq"
    )
    @NotBlank(message = "providerId가 비어있습니다.")
    private String providerId;

    @Schema(
            description = "이름",
            example = "김연수"
    )
    @NotBlank(message = "이름이 비어있습니다.")
    private String name;

    @Schema(
            description = "생년월일",
            example = "2001-05-20"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDay;

    @Schema(
            description = "프로필 이미지 URL",
            example = "https://home.inuappcenter.kr/assets/navbar_logo-CWP_FHvD.svg"
    )
    @NotBlank(message = "프로필 이미지 키값이 비어있습니다")
    private String profileImageObjectKey;

    @Schema(
            description = "FCM Token",
            example = "hasdjfhqiuefhadsfbgqweyugfgfjgkn"
    )
    @NotBlank(message = "Fcm Token이 비어있습니다.")
    private String fcmToken;

    @Schema(
            description = "유저 타입",
            example = "customer"
    )
    @NotBlank
    @Pattern(regexp = "^(customer|owner)$", message = "유효하지 않은 타입입니다.")
    private String userType;

    @Schema(
            description = "광고성 메일 수신 여부",
            example = "true"
    )
    @NotNull(message = "광고성 메일 수신 여부가 비어있습니다.")
    private Boolean acceptAdEmails;
}
