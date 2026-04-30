package org.sopt.like.exception;

import org.sopt.global.api.exception.BaseException;
import org.sopt.like.exception.code.LikeErrorCode;

public class AlreadyLikedException extends BaseException {
    public AlreadyLikedException() {
        super(LikeErrorCode.ALREADY_LIKED);
    }
}
