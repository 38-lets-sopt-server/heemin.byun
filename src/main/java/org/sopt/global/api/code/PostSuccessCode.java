package org.sopt.global.api.code;

import org.springframework.http.HttpStatus;

public enum PostSuccessCode implements ResponseCode {
    POST_CREATED(HttpStatus.CREATED,"게시글 등록 완료!"),
    POST_READ(HttpStatus.OK,"조회 완료!"),
    POST_LIST_READ(HttpStatus.OK,"전체 조회 완료!"),
    POST_UPDATED(HttpStatus.OK,"게시글 수정 완료!"),
    POST_DELETED(HttpStatus.OK,"삭제 완료!"),
    POST_SEARCHED(HttpStatus.OK,"게시글 검색 완료!");

    private final HttpStatus status;
    private final String message;

    PostSuccessCode(HttpStatus status, String message) {
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