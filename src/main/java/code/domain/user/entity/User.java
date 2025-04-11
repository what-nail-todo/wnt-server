package code.domain.user.entity;

import code.domain.user.dto.req.NormalSignUpRequestDto;
import code.domain.user.dto.req.SocialSignUpRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email; // 정아가 지은 기린그림

    @Column(length = 50)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false, name = "profile_image_url")
    private String profileImageObjectKey;

    @Column(nullable = false, name = "fcm_token")
    private String fcmToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, name = "accept_ad_emails")
    private Boolean acceptAdEmails;


    @Builder(builderMethodName = "normalUserBuilder", buildMethodName = "buildNormalUser")
    private User(NormalSignUpRequestDto normalSignUpRequestDto, String password){
        this.email = normalSignUpRequestDto.getEmail();
        this.password = password;
        this.name = normalSignUpRequestDto.getName();
        this.birthday = normalSignUpRequestDto.getBirthDay();
        this.profileImageObjectKey = normalSignUpRequestDto.getProfileImageObjectKey();
        this.fcmToken = normalSignUpRequestDto.getFcmToken();
        this.provider = Provider.NORMAL;
        this.role = normalSignUpRequestDto.getUserType().equals("customer") ? Role.CUSTOMER : Role.OWNER;
        this.acceptAdEmails = normalSignUpRequestDto.getAcceptAdEmails();
    }

    @Builder(builderMethodName = "socialUserBuilder", buildMethodName = "buildSocialUser")
    private User(SocialSignUpRequestDto socialSignUpRequestDto, String email, String password, String provider){
        this.email = email;
        this.password = password;
        this.name = socialSignUpRequestDto.getName();
        this.birthday = socialSignUpRequestDto.getBirthDay();
        this.profileImageObjectKey = socialSignUpRequestDto.getProfileImageObjectKey();
        this.fcmToken = socialSignUpRequestDto.getFcmToken();
        this.provider = Provider.from(provider);
        this.role = socialSignUpRequestDto.getUserType().equals("customer") ? Role.CUSTOMER : Role.OWNER;
        this.acceptAdEmails = socialSignUpRequestDto.getAcceptAdEmails();
    }

    @Builder(builderMethodName = "tempUserBuilder", buildMethodName = "buildTempUser")
    private User(){
        this.role = Role.TEMP;
    }
}
