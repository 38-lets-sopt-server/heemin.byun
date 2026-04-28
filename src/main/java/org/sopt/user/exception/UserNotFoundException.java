package org.sopt.user.exception;

import org.sopt.global.api.exception.BaseException;
import org.sopt.user.exception.code.UserErrorCode;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(UserErrorCode.USER_NOT_FOUND);
    }
}
