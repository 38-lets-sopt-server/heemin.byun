package org.sopt.auth.jwt.exception;

import org.sopt.auth.exception.code.AuthErrorCode;
import org.sopt.global.api.exception.BaseException;

public class EmptyTokenException extends BaseException {
    public EmptyTokenException() {
        super(AuthErrorCode.EMPTY_TOKEN);
    }
}