package org.sopt.post.exception.code;

import org.sopt.global.api.code.ResponseCode;
import org.springframework.http.HttpStatus;

public enum PostErrorCode implements ResponseCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 게시글을 찾을 수 없습니다."),
    POST_LIST_EMPTY(HttpStatus.BAD_REQUEST,"등록된 게시글이 없습니다."),
    POST_TITLE_REQUIRED(HttpStatus.BAD_REQUEST,"제목은 필수입니다"),
    POST_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST,"내용은 필수입니다");

    private final HttpStatus status;
    private final String message;

    PostErrorCode(HttpStatus status,String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() { return status; }

    @Override
    public String getMessage() {
        return message;
    }
}
