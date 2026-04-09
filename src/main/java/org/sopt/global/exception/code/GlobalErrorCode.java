package org.sopt.global.exception.code;

public enum GlobalErrorCode implements ErrorCode {

    INVALID_INPUT("잘못된 입력입니다.");

    private final String message;

    GlobalErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
