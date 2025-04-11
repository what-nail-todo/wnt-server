package code.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "ADMIN"),
    CUSTOMER("ROLE_CUSTOMER", "CUSTOMER"),
    OWNER("ROLE_OWNER", "OWNER"),
    TEMP("ROLE_TEMP", "TEMP")
    ;

    private final String securityRole;
    private final String roleName;

    public List<SimpleGrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }

}