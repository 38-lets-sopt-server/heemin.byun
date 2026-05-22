package org.sopt.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.global.api.code.LikeSuccessCode;
import org.sopt.global.api.response.BaseResponse;
import org.sopt.like.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/likes")
@Tag(name = "좋아요 기능",description = "좋아요 추가 및 취소 관련 API")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    @Operation(summary = "좋아요 추가" ,description = "좋아요를 누릅니다.")
    public ResponseEntity<BaseResponse<Void>> addLike(
            @AuthenticationPrincipal Long memberId,
            @PathVariable Long postId
    ) {
        likeService.addLike(postId, memberId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(LikeSuccessCode.LIKE_ADDED));
    }

    @DeleteMapping
    @Operation(summary = "좋아요 취소" ,description = "이미 누른 좋아요에 대해 좋아요를 취소합니다.")
    public ResponseEntity<BaseResponse<Void>> cancelLike(
            @AuthenticationPrincipal Long memberId,
            @PathVariable Long postId
    ) {
        likeService.cancelLike(postId, memberId);
        return ResponseEntity
                .ok(BaseResponse.success(LikeSuccessCode.LIKE_CANCELLED));
    }
}