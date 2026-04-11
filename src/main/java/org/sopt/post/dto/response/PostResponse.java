package org.sopt.post.dto.response;

import org.sopt.post.domain.Post;

import java.time.format.DateTimeFormatter;

public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        String createdAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getCreatedAt().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                )
        );
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " - " + author + " (" + createdAt + ")\n" + content;
    }
}
