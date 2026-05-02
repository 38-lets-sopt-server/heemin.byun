package org.sopt.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.global.api.code.LikeSuccessCode;
import org.sopt.global.api.response.BaseResponse;
import org.sopt.like.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/likes")
@Tag(name = "좋아요 기능",description = "좋아요 추가 및 취소 관련 API")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/users/{userId}")
    @Operation(summary = "좋아요 추가" ,description = "좋아요를 누릅니다.")
    public ResponseEntity<BaseResponse<Void>> addLike(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        likeService.addLike(postId, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(LikeSuccessCode.LIKE_ADDED));
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "좋아요 취소" ,description = "이미 누른 좋아요에 대해 좋아요를 취소합니다.")
    public ResponseEntity<BaseResponse<Void>> cancelLike(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        likeService.cancelLike(postId, userId);
        return ResponseEntity
                .ok(BaseResponse.success(LikeSuccessCode.LIKE_CANCELLED));
    }
}