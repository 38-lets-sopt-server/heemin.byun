package org.sopt.global.api.code;

import org.springframework.http.HttpStatus;

public enum LikeSuccessCode implements ResponseCode {
    LIKE_ADDED(HttpStatus.CREATED, "좋아요 완료!"),
    LIKE_CANCELLED(HttpStatus.OK, "좋아요 취소 완료!");

    private final HttpStatus status;
    private final String message;

    LikeSuccessCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() { return status; }

    @Override
    public String getMessage() { return message; }
}