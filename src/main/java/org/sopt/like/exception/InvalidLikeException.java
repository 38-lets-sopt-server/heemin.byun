package org.sopt.like.exception;

import org.sopt.global.api.exception.BaseException;
import org.sopt.like.exception.code.LikeErrorCode;

public class InvalidLikeException extends BaseException {
    public InvalidLikeException() {
        super(LikeErrorCode.USER_ID_REQUIRED);
    }
}
