package org.sopt.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.global.api.code.PostSuccessCode;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostListResponse;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.global.api.response.BaseResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@Tag(name = "게시글 작성",description = "게시글 작성,수정,조회,삭제 관련 API")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @Operation(summary = "게시글 작성" ,description = "멤버id를 받아 게시글을 작성합니다.")
    public ResponseEntity<BaseResponse<PostResponse>> createPost(@RequestBody CreatePostRequest request) {
        PostResponse post = postService.createPost(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(PostSuccessCode.POST_CREATED, post));
    }

    @GetMapping
    @Operation(summary = "게시글 전체 조회" ,description = "전체 게시글 리스트를 반환합니다.")
    public ResponseEntity<BaseResponse<List<PostListResponse>>> getAllPosts() {
        List<PostListResponse> posts = postService.readAllPosts();
        return ResponseEntity.ok(BaseResponse.success(PostSuccessCode.POST_LIST_READ, posts));
    }

    @GetMapping("/{id}")
    @Operation(summary = "게시글 단건 조회" ,description = "단건 게시글을 조회합니다.")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @Parameter(description = "조회할 게시글의 ID", example = "1", required = true)
            @PathVariable final Long id) {
        PostResponse post = postService.readPost(id);
        return ResponseEntity.ok(BaseResponse.success(PostSuccessCode.POST_READ, post));
    }

    @PutMapping("{id}")
    @Operation(summary = "게시글 수정" ,description = "존재하던 게시글을 수정합니다.")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(
            @Parameter(description = "조회할 게시글의 ID", example = "1", required = true)
            @PathVariable final Long id,
            @RequestBody UpdatePostRequest request) {
        PostResponse post = postService.updatePost(id, request);
        return ResponseEntity.ok(BaseResponse.success(PostSuccessCode.POST_UPDATED, post));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제" ,description = "게시글을 삭제합니다.")
    public ResponseEntity<BaseResponse<Void>> deletePost(
            @Parameter(description = "조회할 게시글의 ID", example = "1", required = true)
            @PathVariable final Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(BaseResponse.success(PostSuccessCode.POST_DELETED));
    }

    @GetMapping("/search")
    @Operation(summary = "게시글 검색" ,description = "게시글을 제목으로 검색합니다.")
    public ResponseEntity<BaseResponse<List<PostListResponse>>> searchPosts(
            @RequestParam String keyword
    ) {
        List<PostListResponse> posts = postService.searchByTitle(keyword);
        return ResponseEntity.ok(BaseResponse.success(PostSuccessCode.POST_SEARCHED, posts));
    }
}