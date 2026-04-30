package org.sopt.global.api.code;


import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ResponseCode {

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청 형식입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    SAME_TIME_REQUEST(HttpStatus.CONFLICT,"동시에 온 요청입니다.");

    private final HttpStatus status;
    private final String message;

    GlobalErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}


