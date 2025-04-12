package code.global.exception.handler;

import code.domain.oauth.util.OAuth2AuthorizationRequestRepository;
import code.global.security.jwt.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static code.domain.oauth.util.OAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        log.error("[onAuthenticationFailure] : OAuth2 Authorization Failed");

        // TODO orElse 주소값 변경 필요
        String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM)
                .map(Cookie::getValue)
                .orElse("localhost");

        // TODO path 주소값 변경 필요
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .path("/error")
                .build()
                .toUriString();

        oAuth2AuthorizationRequestRepository.removeAuthorizationRequest(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
