package org.sopt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.code.AuthSuccessCode;
import org.sopt.auth.dto.request.SignUpRequest;
import org.sopt.auth.service.AuthService;
import org.sopt.auth.dto.TokenResponse;
import org.sopt.auth.util.AccessTokenResolver;
import org.sopt.auth.util.CookieUtil;
import org.sopt.auth.util.HeaderUtil;
import org.sopt.global.api.response.BaseResponse;
import org.sopt.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.sopt.auth.dto.request.LoginRequest;
import org.sopt.auth.dto.request.KakaoLoginRequest;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입 (Access Token + Refresh Token 발급)")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<Void>> signup(
            @RequestBody @Valid final SignUpRequest request,
            HttpServletResponse response
    ) {
        TokenResponse tokens = authService.signUp(request);
        setTokens(response, tokens);
        return ResponseEntity.ok(BaseResponse.success(AuthSuccessCode.SIGNUP_SUCCESS));
    }


    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<Void>> login(
            @RequestBody @Valid final LoginRequest request,
            HttpServletResponse response
    ) {
        TokenResponse tokens = authService.login(request);
        setTokens(response, tokens);
        return ResponseEntity.ok(BaseResponse.success(AuthSuccessCode.LOGIN_SUCCESS));
    }

    @Operation(summary = "카카오 로그인 (인가코드로 토큰 발급)")
    @PostMapping("/kakao")
    public ResponseEntity<BaseResponse<Void>> kakaoLogin(
            @RequestBody @Valid final KakaoLoginRequest request,
            HttpServletResponse response
    ) {
        TokenResponse tokens = authService.kakaoLogin(request.code());
        setTokens(response, tokens);
        return ResponseEntity.ok(BaseResponse.success(AuthSuccessCode.KAKAO_LOGIN_SUCCESS));
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<BaseResponse<Void>> reissue(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = CookieUtil.getRefreshToken(request);
        TokenResponse tokens = authService.reissue(refreshToken);
        setTokens(response, tokens);
        return ResponseEntity.ok(BaseResponse.success(AuthSuccessCode.TOKEN_GENERATED));
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(
            @AuthenticationPrincipal Long memberId
    ) {
        UserResponse member = authService.getMemberById(memberId);
        return ResponseEntity.ok(BaseResponse.success(AuthSuccessCode.USER_SEARCHED,member));
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(
            @AuthenticationPrincipal Long memberId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String accessToken = AccessTokenResolver.resolve(request);
        authService.logout(memberId,accessToken);
        CookieUtil.deleteCookie(response, "refreshToken");
        return ResponseEntity.ok(BaseResponse.success(AuthSuccessCode.LOGOUT_SUCCESS));
    }

    private void setTokens(HttpServletResponse response, TokenResponse tokens) {
        HeaderUtil.setAuthorizationHeader(response, tokens.accessToken());
        CookieUtil.addRefreshTokenCookie(response, tokens.refreshToken());
    }
}