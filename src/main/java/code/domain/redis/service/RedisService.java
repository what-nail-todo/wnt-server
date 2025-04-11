package code.domain.redis.service;

import code.domain.user.entity.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
}
