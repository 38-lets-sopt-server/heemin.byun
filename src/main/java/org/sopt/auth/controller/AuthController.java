package org.sopt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.code.AuthSuccessCode;
import org.sopt.auth.service.AuthService;
import org.sopt.auth.dto.TokenResponse;
import org.sopt.global.api.response.BaseResponse;
import org.sopt.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인 (Access Token + Refresh Token 발급)")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<TokenResponse>> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        TokenResponse tokens = authService.login(email, password);
        //todo: 리프레시 토큰 쿠키로 담아서 보내도록 helper metohd private으로 하기 !!
        return ResponseEntity.ok(BaseResponse.success(AuthSuccessCode.TOKEN_GENERATED,tokens));
    }

    @Operation(summary = "내 정보 조회 (Access Token 검증)")
    @GetMapping("/api/v1/me")
    public ResponseEntity<BaseResponse<UserResponse>> me(Authentication authentication) {

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("인증되지 않았습니다.");
        }

        Long memberId = Long.parseLong(authentication.getName());
        UserResponse member = authService.getMemberById(memberId);

        return ResponseEntity.ok(BaseResponse.success(AuthSuccessCode.USER_SEARCHED,member));
    }
}