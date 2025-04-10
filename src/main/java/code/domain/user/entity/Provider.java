package code.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {

    NORMAL("Normal"),
    KAKAO("Kakao"),
    GOOGLE("Google"),
    NAVER("Naver");

    private final String provider;

}
