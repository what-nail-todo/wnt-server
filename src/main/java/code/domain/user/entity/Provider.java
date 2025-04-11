package code.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Provider {

    NORMAL("normal"),
    KAKAO("kakao"),
    GOOGLE("google"),
    NAVER("naver");

    @JsonValue
    private final String provider;

    @JsonCreator
    public static Provider from(String provider){
        return Arrays.stream(values())
                .filter(p -> p.getProvider().equals(provider))
                .findAny()
                .orElse(null);
    }
}
