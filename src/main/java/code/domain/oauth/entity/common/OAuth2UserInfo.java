package code.domain.oauth.entity.common;

import code.domain.user.entity.Provider;

import java.util.Map;

public interface OAuth2UserInfo {
    Provider getProvider();

    String getAccessToken();

    Map<String, Object> getAttributes();

    String getId();

    String getName();

    String getEmail();

    String getProfileImageUrl();
}
