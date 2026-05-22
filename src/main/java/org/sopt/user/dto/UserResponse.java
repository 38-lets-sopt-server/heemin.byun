package org.sopt.user.dto;

import org.sopt.user.entity.User;

public record UserResponse(
        Long id,
        String nickname,
        String email
) {

    public static UserResponse of(Long id, String nickname ,String email) {
        return new UserResponse(id,nickname,email);
    }

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail()
        );
    }
}