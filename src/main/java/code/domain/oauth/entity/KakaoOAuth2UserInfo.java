package code.domain.oauth.entity;

import code.domain.oauth.entity.common.OAuth2UserInfo;
import code.domain.user.entity.Provider;

import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    private final String accessToken;
    private final String id;
    private final String email;
    private final String name;
    private final String profileImageUrl;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes, String accessToken){
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        this.attributes = profile;
        this.accessToken = accessToken;

        this.id = ((Long) attributes.get("id")).toString();
        this.email = (String) kakaoAccount.get("email");
        this.profileImageUrl = (String) attributes.get("profile_image_url");

        this.attributes.put("id", id);
        this.attributes.put("email", this.email);

        this.name = "";
    }

    @Override
    public Provider getProvider() {
        return Provider.KAKAO;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
