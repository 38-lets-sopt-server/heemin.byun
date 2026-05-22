package org.sopt.auth.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoService {

    private final WebClient webClient;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public KakaoService(
            @Value("${kakao.client-id}") String clientId,
            @Value("${kakao.client-secret}") String clientSecret,
            @Value("${kakao.redirect-uri}") String redirectUri
    ) {
        this.webClient = WebClient.builder().build();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String getAccessToken(String code) {
        JsonNode response = webClient.post()
                .uri("https://kauth.kakao.com/oauth/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("redirect_uri", redirectUri)
                        .with("code", code))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        return response.get("access_token").asText();
    }

    public KakaoUserInfo getUserInfo(String accessToken) {
        JsonNode response = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        Long kakaoId = response.get("id").asLong();
        String nickname = response.get("properties").get("nickname").asText();

        String email = null;
        JsonNode kakaoAccount = response.get("kakao_account");
        if (kakaoAccount != null && kakaoAccount.has("email")) {
            email = kakaoAccount.get("email").asText();
        }

        return new KakaoUserInfo(kakaoId, nickname,email);
    }

    public record KakaoUserInfo(Long kakaoId, String nickname,String email) {}
}