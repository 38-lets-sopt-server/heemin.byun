package org.sopt.auth.jwt.exception;

import org.sopt.auth.exception.code.AuthErrorCode;
import org.sopt.global.api.exception.BaseException;

public class MalformedTokenException extends BaseException {
    public MalformedTokenException() {
        super(AuthErrorCode.MALFORMED_TOKEN);
    }
}
