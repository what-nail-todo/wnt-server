package code.domain.oauth.entity;

import code.domain.oauth.entity.common.OAuth2UserInfo;
import code.domain.user.entity.Provider;

import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    private final String accessToken;
    private final String id;
    private final String email;
    private final String name;
    private final String profileImageUrl;

    public GoogleOAuth2UserInfo(Map<String, Object> attributes, String accessToken){
        this.attributes = attributes;
        this.accessToken = accessToken;
        this.id = (String) attributes.get("sub");
        this.email = (String) attributes.get("email");
        this.name = (String) attributes.get("name");
        this.profileImageUrl = (String) attributes.get("picture");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Provider getProvider() {
        return Provider.GOOGLE;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
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
