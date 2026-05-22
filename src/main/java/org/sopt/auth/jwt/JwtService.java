package org.sopt.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.sopt.auth.exception.code.AuthErrorCode;
import org.sopt.global.api.exception.BaseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final Algorithm algorithm;
    private final long accessTokenExpiresInSeconds;
    private final long refreshTokenExpiresInSeconds;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-token-expires-in-seconds:1800}") long accessTokenExpiresInSeconds,
            @Value("${security.jwt.refresh-token-expires-in-seconds:1209600}") long refreshTokenExpiresInSeconds
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.accessTokenExpiresInSeconds = accessTokenExpiresInSeconds;
        this.refreshTokenExpiresInSeconds = refreshTokenExpiresInSeconds;
    }

    public String generateAccessToken(Long memberId, String email) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(memberId))
                .withClaim("email", email)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(accessTokenExpiresInSeconds)))
                .sign(algorithm);
    }

    public String generateRefreshToken(Long memberId) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(String.valueOf(memberId))
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(refreshTokenExpiresInSeconds)))
                .sign(algorithm);
    }

    public Long verifyAndGetMemberId(String token) {

        if (token == null || token.isBlank()) {
            throw new BaseException(AuthErrorCode.EMPTY_TOKEN);
        }

        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return Long.parseLong(jwt.getSubject());
        } catch (TokenExpiredException e) {
            throw new BaseException(AuthErrorCode.EXPIRED_TOKEN);
        } catch (JWTVerificationException e) {
            throw new BaseException(AuthErrorCode.MALFORMED_TOKEN);
        } catch (NumberFormatException e) {
            throw new BaseException(AuthErrorCode.MALFORMED_TOKEN);
        }
    }

    public long getRemainingMillis(String token) {
        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            Date expiration = jwt.getExpiresAt();
            return expiration.getTime() - System.currentTimeMillis();
        } catch (TokenExpiredException e) {
            return 0;
        } catch (JWTVerificationException e) {
            throw new BaseException(AuthErrorCode.MALFORMED_TOKEN);
        }
    }
}