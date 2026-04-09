package org.sopt.post.exception;

import org.sopt.global.exception.BaseException;
import org.sopt.post.exception.code.PostErrorCode;

public class PostNotFoundException extends BaseException {
    public PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}
