package code.domain.oauth.util;

import code.global.security.jwt.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class OAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    public static final String OAUTH2_AUTHORIZATION_REQUEST = "oauth2_authorization_request";
    public static final String REDIRECT_URI_PARAM = "redirect_uri";
    private static final int COOKIE_EXPIRED_TIME = 10800000;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request){
        return CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST)
                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authReq, HttpServletRequest request, HttpServletResponse response){
        if(authReq == null){
            CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST);
            CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM);

            return;
        }

        CookieUtil.setCookie(response, OAUTH2_AUTHORIZATION_REQUEST, CookieUtil.serialize(authReq), COOKIE_EXPIRED_TIME);

        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM);

        if(StringUtils.hasText(redirectUriAfterLogin))
            CookieUtil.setCookie(response, REDIRECT_URI_PARAM, redirectUriAfterLogin, COOKIE_EXPIRED_TIME);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse res){
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST);
        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM);
    }
}
