package org.sopt.auth.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RefreshTokenService {

    private static final String PREFIX = "refresh:";
    private static final long EXPIRATION_DAYS = 7;

    private final RedisTemplate<String, String> redisTemplate;

    public RefreshTokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(Long memberId, String refreshToken) {
        redisTemplate.opsForValue().set(
                PREFIX + memberId,
                refreshToken,
                EXPIRATION_DAYS,
                TimeUnit.DAYS
        );
    }

    public String get(Long memberId) {
        return redisTemplate.opsForValue().get(PREFIX + memberId);
    }

    public void delete(Long memberId) {
        redisTemplate.delete(PREFIX + memberId);
    }
}