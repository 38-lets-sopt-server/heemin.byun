package org.sopt.post.exception;

import org.sopt.global.exception.BaseException;
import org.sopt.post.exception.code.PostErrorCode;

public class InvalidContentException extends BaseException {
    public InvalidContentException() {
        super(PostErrorCode.POST_CONTENT_REQUIRED);
    }
}
