package org.sopt.post.controller;

import org.sopt.global.api.code.PostSuccessCode;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.global.api.response.BaseResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST /posts
    @PostMapping
    public ResponseEntity<BaseResponse<PostResponse>> createPost(@RequestBody CreatePostRequest request) {
        PostResponse post = postService.createPost(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(PostSuccessCode.POST_CREATED, post));
    }

    // GET /posts 📝 과제
    @GetMapping
    public ResponseEntity<BaseResponse<List<PostResponse>>> getAllPosts() {
        // TODO: postService.getAllPosts() 호출해서 반환
        List<PostResponse> posts = postService.readAllPosts();
        return ResponseEntity.ok(BaseResponse.success(PostSuccessCode.POST_LIST_READ, posts));
    }

    // GET /posts/{id} 📝 과제
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<PostResponse>> getPost(
            @PathVariable final Long id) {
        // TODO: postService.getPost(id) 호출, 예외 발생 시 null 반환
        PostResponse post = postService.readPost(id);
        return ResponseEntity.ok(BaseResponse.success(PostSuccessCode.POST_READ, post));
    }

    // PUT /posts/{id} 📝 과제
    @PutMapping("{id}")
    public ResponseEntity<BaseResponse<PostResponse>> updatePost(
            @PathVariable final Long id,
            @RequestBody UpdatePostRequest request) {
        // TODO: postService.updatePost() 호출, 예외 발생 시 에러 메시지 출력
        PostResponse post = postService.updatePost(id, request);
        return ResponseEntity.ok(BaseResponse.success(PostSuccessCode.POST_UPDATED, post));
    }

    // DELETE /posts/{id} 📝 과제
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePost(@PathVariable final Long id) {
        // TODO: postService.deletePost() 호출, 예외 발생 시 에러 메시지 출력
        postService.deletePost(id);
        return ResponseEntity.ok(BaseResponse.success(PostSuccessCode.POST_DELETED));
    }
}