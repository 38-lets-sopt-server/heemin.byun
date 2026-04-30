package org.sopt.like.exception;

import org.sopt.global.api.exception.BaseException;
import org.sopt.like.exception.code.LikeErrorCode;

public class LikeNotFoundException extends BaseException {
    public LikeNotFoundException() {
        super(LikeErrorCode.LIKE_NOT_FOUND);
    }
}