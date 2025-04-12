package code.domain.oauth.util;

import code.domain.oauth.entity.GoogleOAuth2UserInfo;
import code.domain.oauth.entity.KakaoOAuth2UserInfo;
import code.domain.oauth.entity.NaverOAuth2UserInfo;
import code.domain.oauth.entity.common.OAuth2UserInfo;
import code.domain.user.entity.Provider;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes, String accessToken){

        if (Provider.GOOGLE.getProvider().equals(provider))
            return new GoogleOAuth2UserInfo(attributes, accessToken);

        if (Provider.KAKAO.getProvider().equals(provider))
            return new KakaoOAuth2UserInfo(attributes, accessToken);

        if (Provider.NAVER.getProvider().equals(provider))
            return new NaverOAuth2UserInfo(attributes, accessToken);

        throw new OAuth2AuthenticationException("Not acceptable provider : " + provider);
    }

}