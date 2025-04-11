package code.domain.oauth.entity;

import code.domain.oauth.entity.common.OAuth2UserInfo;
import code.domain.user.entity.Provider;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;

    private final String accessToken;
    private final String id;
    private final String email;
    private final String name;
    private final String firstName;
    private final String lastName;
    private final String nickName;
    private final String profileImageUrl;

    public NaverOAuth2UserInfo(Map<String, Object> attributes, String accessToken){
        this.attributes = (Map<String, Object>) attributes.get("response");
        this.accessToken = accessToken;

        this.id = (String) this.attributes.get("id");
        this.email = (String) this.attributes.get("email");
        this.name = (String) this.attributes.get("name");
        this.nickName = (String) attributes.get("nickname");
        this.profileImageUrl = (String) attributes.get("profile_image");

        this.firstName = null;
        this.lastName = null;
    }

    @Override
    public Provider getProvider() {
        return Provider.NAVER;
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
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getNickname() {
        return nickName;
    }

    @Override
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
