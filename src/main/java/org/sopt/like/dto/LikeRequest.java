package org.sopt.like.dto;

import org.sopt.like.exception.InvalidLikeException;

public record LikeRequest(Long userId) {
    public LikeRequest {
        if (userId == null) {
            throw new InvalidLikeException();
        }
    }
}