package code.global.security.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "로그인 Response")
@Getter
public class TokenResponse {

    @Schema(
            description = "액세스 토큰",
            example = "eyJhbGciOiJIUzI1VCJ9.eyJzdWUiIsImlhdCI6MTcwNjNzA2MDc4MjAwfQ.1234abcd5678efgh9012ijkl3456mnop"
    )
    private final String accessToken;

    @Schema(
            description = "만료 시간",
            example = "3030000"
    )
    private final long expiredTime;

    @Builder
    private TokenResponse(String accessToken, long expiredTime){
        this.accessToken = accessToken;
        this.expiredTime = expiredTime;
    }
}
