package org.sopt.post.exception.code;

import org.sopt.global.exception.code.ResponseCode;

public enum PostResponseCode implements ResponseCode {
    POST_NOT_FOUND("해당 게시글을 찾을 수 없습니다."),
    POST_LIST_EMPTY("등록된 게시글이 없습니다."),
    POST_TITLE_REQUIRED("제목은 필수입니다"),
    POST_CONTENT_REQUIRED("내용은 필수입니다");

    private final String message;

    PostResponseCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
