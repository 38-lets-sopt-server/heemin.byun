package org.sopt.user.exception.code;

import org.sopt.global.api.code.ResponseCode;
import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ResponseCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당하는 유저가 없습니다.");

    private final HttpStatus status;
    private final String message;

    UserErrorCode(HttpStatus status,String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() { return status; }

    @Override
    public String getMessage() {
        return message;
    }
}
