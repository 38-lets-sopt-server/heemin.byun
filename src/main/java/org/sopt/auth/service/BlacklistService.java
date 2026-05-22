package org.sopt.auth.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BlacklistService {

    private static final String PREFIX = "blacklist:";

    private final RedisTemplate<String, String> redisTemplate;

    public void add(String accessToken, long remainingMillis) {
        redisTemplate.opsForValue().set(
                PREFIX + accessToken,
                "logout",
                remainingMillis,
                TimeUnit.MILLISECONDS
        );
    }

    public boolean isBlacklisted(String accessToken) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + accessToken));
    }
}