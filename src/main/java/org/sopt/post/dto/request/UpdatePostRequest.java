package org.sopt.post.dto.request;

import org.sopt.post.exception.InvalidContentException;
import org.sopt.post.exception.InvalidTitleException;

public record UpdatePostRequest(
        String title,
        String content
) {
    public UpdatePostRequest {
        if (title == null || title.isBlank()) {
            throw new InvalidTitleException();
        }
        if (content == null || content.isBlank()) {
            throw new InvalidContentException();
        }
    }
}