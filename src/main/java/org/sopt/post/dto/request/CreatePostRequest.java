package org.sopt.post.dto.request;

import org.sopt.post.exception.InvalidContentException;
import org.sopt.post.exception.InvalidTitleException;

public record CreatePostRequest(
        String title,
        String content,
        Long userId
) {
    public CreatePostRequest {
        if (title == null || title.isBlank()) {
            throw new InvalidTitleException();
        }
        if (content == null || content.isBlank()) {
            throw new InvalidContentException();
        }
    }
}

