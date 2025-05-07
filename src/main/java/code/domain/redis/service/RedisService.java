package code.domain.redis.service;

import code.domain.user.entity.Provider;
import code.global.exception.entity.CustomErrorCode;
import code.global.exception.entity.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;

    public void saveOAuth2UserInfo(String providerId, String email, Provider provider){

        hashOperations.put(providerId, "email", email);
        hashOperations.put(providerId, "provider", provider.name());

        redisTemplate.expire(providerId, 1, TimeUnit.HOURS);
    }

    public Map<String, String> getOAuth2UserInfo(String providerId){
        return hashOperations.entries(providerId);
    }

    public void saveAuthCode(String email, String authCode){

        hashOperations.put(email, "auth-code", authCode);
        hashOperations.put(email,"verified", "false");

        redisTemplate.expire(email, 4, TimeUnit.MINUTES);
    }

    public Boolean verifyAuthCode(String email, String inputCode) {

        String storedCode = hashOperations.get(email, "auth-code");

        if (storedCode == null)
            throw new RestApiException(CustomErrorCode.NO_AUTH_CODE_EMAIL);

        if (storedCode.equals(inputCode)) {
            redisTemplate.persist(email);
            hashOperations.put(email, "verified", "true");

            log.info("[ verifyAuthCode() ] : 메일 인증 성공 \"email = {}\"", email);
            return true;
        }

        log.warn("[ verifyAuthCode() ] : 메일 인증 살퍄 \"email = {}\"", email);
        return false;
    }

    public Boolean getEmailVerification(String email){

//        Boolean result = Boolean.parseBoolean(hashOperations.get(email, "verified"));
//
//        redisTemplate.delete(email);

        return true;
    }
}
