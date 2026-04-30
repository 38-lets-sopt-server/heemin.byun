package org.sopt.post.dto.response;

import org.sopt.post.domain.Post;

import java.time.format.DateTimeFormatter;

public record PostResponse(
        Long id,
        String title,
        String content,
        String nickname,
        String createdAt
) {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getCreatedAt().format(FORMATTER)
        );
    }
}
