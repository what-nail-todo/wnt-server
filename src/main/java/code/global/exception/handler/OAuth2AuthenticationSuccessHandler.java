package code.global.exception.handler;

import code.domain.oauth.entity.common.OAuth2UserDetailsImpl;
import code.domain.oauth.service.CustomOAuth2UserService;
import code.domain.oauth.util.OAuth2AuthorizationRequestRepository;
import code.domain.redis.service.RedisService;
import code.global.security.domain.TokenResponse;
import code.global.security.jwt.util.CookieUtil;
import code.global.security.jwt.util.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

import static code.domain.oauth.util.OAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CustomOAuth2UserService oAuth2UserService;
    private final RedisService redisService;

    private final JwtProvider jwtProvider;
    private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException{

        OAuth2UserDetailsImpl oAuth2UserDetails = (OAuth2UserDetailsImpl) authentication.getPrincipal();

        String targetUrl = setTargetUrl(request, response, oAuth2UserDetails);

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String setTargetUrl(HttpServletRequest request, HttpServletResponse response, OAuth2UserDetailsImpl oAuth2UserDetails){
        Optional<String> redirectUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM)
                .map(Cookie::getValue);

        String targetUrl = redirectUrl.orElse(getDefaultTargetUrl());

        if(oAuth2UserService.checkUserPresent(oAuth2UserDetails.getName())){
            TokenResponse tokenResponse = jwtProvider.createToken(oAuth2UserDetails.getName());

            response.addHeader(HttpHeaders.SET_COOKIE, CookieUtil.createCookie("access-token", tokenResponse.getAccessToken(), tokenResponse.getExpiredTime()).toString());

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .path("/main")
                    .build()
                    .toUriString();
        }else {
            response.addHeader(HttpHeaders.SET_COOKIE, CookieUtil.createCookie("provider-id", oAuth2UserDetails.getUserInfo().getId()).toString());

            redisService.saveOAuth2UserInfo(oAuth2UserDetails.getUserInfo().getId(), oAuth2UserDetails.getName(), oAuth2UserDetails.getUserInfo().getProvider());

            return UriComponentsBuilder.fromUriString(targetUrl)
                    .path("/sign-up")
                    .build()
                    .toUriString();
        }
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response){
        super.clearAuthenticationAttributes(request);
        oAuth2AuthorizationRequestRepository.removeAuthorizationRequest(request, response);
    }
}
