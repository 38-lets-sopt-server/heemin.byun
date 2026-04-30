package org.sopt.like.exception.code;

import org.sopt.global.api.code.ResponseCode;
import org.springframework.http.HttpStatus;

public enum LikeErrorCode implements ResponseCode {
    ALREADY_LIKED(HttpStatus.CONFLICT, "이미 좋아요를 누른 게시글입니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요를 누르지 않은 게시글입니다."),
    USER_ID_REQUIRED(HttpStatus.BAD_REQUEST,"유저 아이디는 필수입니다.");

    private final HttpStatus status;
    private final String message;

    LikeErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() { return status; }

    @Override
    public String getMessage() { return message; }
}