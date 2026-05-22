package org.sopt.auth.exception.code;

import org.sopt.global.api.code.ResponseCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ResponseCode {
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    KAKAO_USER_CANNOT_LOGIN(HttpStatus.BAD_REQUEST, "카카오로 가입한 유저입니다. 카카오 로그인을 이용해주세요."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    SIGNUP_REQUIRED(HttpStatus.NOT_FOUND,"회원가입이 필요합니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 비어있습니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 형식이 올바르지 않습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");

    private final HttpStatus status;
    private final String message;

    AuthErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() { return status; }

    @Override
    public String getMessage() { return message; }
}
