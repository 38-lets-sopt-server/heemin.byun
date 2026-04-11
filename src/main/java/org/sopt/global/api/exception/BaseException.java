package org.sopt.global.api.exception;

import org.sopt.global.api.code.ResponseCode;

public class BaseException extends RuntimeException {
    private final ResponseCode responseCode;

    public BaseException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public ResponseCode getErrorCode() {
        return responseCode;
    }
}