package org.sopt.auth.code;

import lombok.Getter;
import org.sopt.global.api.code.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthSuccessCode implements ResponseCode {
    TOKEN_GENERATED(HttpStatus.CREATED, "토큰 발급 완료!"),
    USER_SEARCHED(HttpStatus.OK,"유저 조회 완료!");

    private final HttpStatus status;
    private final String message;

    AuthSuccessCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}

