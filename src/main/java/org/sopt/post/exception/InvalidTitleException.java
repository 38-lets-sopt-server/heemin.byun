package org.sopt.post.exception;

import org.sopt.global.api.exception.BaseException;
import org.sopt.post.exception.code.PostErrorCode;

public class InvalidTitleException extends BaseException {
  public InvalidTitleException() {
    super(PostErrorCode.POST_TITLE_REQUIRED);
  }
}

