package code.domain.oauth.service;

import code.domain.oauth.entity.common.OAuth2UserDetailsImpl;
import code.domain.oauth.entity.common.OAuth2UserInfo;
import code.domain.oauth.util.OAuth2UserInfoFactory;
import code.domain.user.repository.UserRepository;
import code.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException{

        OAuth2User oAuth2User = super.loadUser(request);

        return convertToOAuth2UserDetailsImpl(request, oAuth2User);
    }

    private OAuth2User convertToOAuth2UserDetailsImpl(OAuth2UserRequest request, OAuth2User oAuth2User){

        String provider = request.getClientRegistration().getRegistrationId();
        String accessToken = request.getAccessToken().getTokenValue();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, oAuth2User.getAttributes(), accessToken);

        User user = userRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElseGet(() -> User.tempUserBuilder().buildTempUser());

        log.info("[ convertToOAuth2UserDetailsImpl() ] : 유저 정보 로드 완료 \"email = {}\"", oAuth2UserInfo.getEmail());

        return new OAuth2UserDetailsImpl(oAuth2UserInfo, user.getRole());
    }
}
