package org.sopt.global.api.code;

public enum PostSuccessCode implements ResponseCode {
    POST_CREATED("게시글 등록 완료!"),
    POST_READ("조회 완료!"),
    POST_LIST_READ("전체 조회 완료!"),
    POST_UPDATED("게시글 수정 완료!"),
    POST_DELETED("삭제 완료!");

    private final String message;

    PostSuccessCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}