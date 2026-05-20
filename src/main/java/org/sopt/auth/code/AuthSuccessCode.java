package org.sopt.auth.code;

import lombok.Getter;
import org.sopt.global.api.code.ResponseCode;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthSuccessCode implements ResponseCode {
    LOGIN_SUCCEED(HttpStatus.OK,"로그인 성공!"),
    KAKAO_LOGIN_SUCCEED(HttpStatus.OK,"카카오 로그인 성공!"),
    SIGNUP_SUCCEED(HttpStatus.OK,"회원가입 성공!"),
    TOKEN_GENERATED(HttpStatus.CREATED, "토큰 발급 완료!"),
    USER_SEARCHED(HttpStatus.OK,"유저 조회 완료!"),
    LOGOUT_SUCCESS(HttpStatus.OK,"로그아웃 성공!")
    ;

    private final HttpStatus status;
    private final String message;

    AuthSuccessCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}

