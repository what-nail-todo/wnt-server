package code.domain.oauth.entity.common;

import code.domain.user.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class OAuth2UserDetailsImpl implements UserDetails, OAuth2User {

    private final OAuth2UserInfo oAuth2UserInfo;
    private final Role role;

    public OAuth2UserDetailsImpl(OAuth2UserInfo oAuth2UserInfo, Role role) {
        this.oAuth2UserInfo = oAuth2UserInfo;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2UserInfo.getAttributes();
    }

    @Override
    public String getUsername() {
        return oAuth2UserInfo.getEmail();
    }

    @Override
    public String getName() {
        return oAuth2UserInfo.getEmail();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role.name()));

        return roles;
    }

    public OAuth2UserInfo getUserInfo() {
        return oAuth2UserInfo;
    }
}
