package code.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

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
    private Role role;

    @Builder
    private User(String email, String password, String name, LocalDate birthday,
                 String profileImageObjectKey, String fcmToken, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.profileImageObjectKey = profileImageObjectKey;
        this.fcmToken = fcmToken;
        this.role = role;
    }
}
