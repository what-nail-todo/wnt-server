package code.domain.user.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "일반 회원가입 Request")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalSignUpRequestDto {

    @Schema(
            description = "이메일",
            example = "whatnailtodo@naver.com"
    )
    @Size(max = 50, message = "이메일 길이 제한을 초과하였습니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;

    @Schema(
            description = "비밀번호",
            example = "WhatNailTodo123!"
    )
    @Size(max = 100, message = "비밀번호 길이 제한을 초과하였습니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?])(?=.*[a-z]).{8,15}$", message = "비밀번호 구성 조건을 충족하지 않습니다.")
    private String password;

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
    @Pattern(regexp = "^(CUSTOMER|OWNER|ADMIN|TEMP)$", message = "유효하지 않은 타입입니다.")
    private String userType;

    @Schema(
            description = "광고성 메일 수신 여부",
            example = "true"
    )
    @NotNull(message = "광고성 메일 수신 여부가 비어있습니다.")
    private Boolean acceptAdEmails;
}
