package org.sopt.user.dto;

import org.sopt.user.entity.User;

public record UserResponse(
        Long id,
        String nickname,
        String password,
        String email
) {

    public static UserResponse of(Long id, String nickname,String password, String email) {
        return new UserResponse(id,nickname, password,email);
    }

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.getPassword(),
                user.getEmail()
        );
    }
}