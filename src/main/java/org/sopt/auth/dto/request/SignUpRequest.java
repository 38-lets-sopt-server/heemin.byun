package org.sopt.auth.dto.request;

public record SignUpRequest(
        String nickname,
        String email,
        String password
) {}