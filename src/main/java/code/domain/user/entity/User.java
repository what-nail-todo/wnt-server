package code.domain.user.entity;

import code.domain.user.dto.req.SignUpRequestDto;
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

    @Builder
    private User(SignUpRequestDto signUpRequestDto, Provider provider, String password){
        this.email = signUpRequestDto.getEmail();
        this.password = password;
        this.name = signUpRequestDto.getName();
        this.birthday = signUpRequestDto.getBirthDay();
        this.profileImageObjectKey = signUpRequestDto.getProfileImageObjectKey();
        this.fcmToken = signUpRequestDto.getFcmToken();
        this.provider = provider;
        this.role = signUpRequestDto.getUserType().equals("customer") ? Role.CUSTOMER : Role.OWNER;
    }
}
