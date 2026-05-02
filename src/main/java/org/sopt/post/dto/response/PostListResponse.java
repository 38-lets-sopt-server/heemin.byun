package org.sopt.post.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record PostListResponse(
        Long id,
        String title,
        String content,
        String nickname,
        String createdAt,
        long likeCount
) {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public PostListResponse(Long id, String title, String content,
                            String nickname, LocalDateTime createdAt, long likeCount) {
        this(id, title, content, nickname,
                createdAt.format(FORMATTER), likeCount);
    }
}