package org.sopt.post.controller;

import org.sopt.global.api.code.PostSuccessCode;
import org.sopt.global.api.exception.BaseException;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.global.api.response.ApiResponse;
import org.sopt.post.service.PostService;

import java.util.List;

public class PostController {
    private final PostService postService = new PostService();

    // POST /posts
    public ApiResponse<PostResponse> createPost(CreatePostRequest request) {
        try {
            PostResponse post = postService.createPost(request);
            return ApiResponse.success(PostSuccessCode.POST_CREATED, post);
        } catch (BaseException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // GET /posts 📝 과제
    public ApiResponse<List<PostResponse>> getAllPosts() {
        // TODO: postService.getAllPosts() 호출해서 반환
        try {
            List<PostResponse> posts = postService.readAllPosts();
            return ApiResponse.success(PostSuccessCode.POST_LIST_READ, posts);
        } catch (BaseException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // GET /posts/{id} 📝 과제
    public ApiResponse<PostResponse> getPost(Long id) {
        // TODO: postService.getPost(id) 호출, 예외 발생 시 null 반환
        try {
            PostResponse post = postService.readPost(id);
            return ApiResponse.success(PostSuccessCode.POST_READ, post);
        } catch (BaseException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // PUT /posts/{id} 📝 과제
    public ApiResponse<PostResponse> updatePost(Long id, UpdatePostRequest request) {
        // TODO: postService.updatePost() 호출, 예외 발생 시 에러 메시지 출력
        try {
            //UpdatePostRequest request = new UpdatePostRequest(newTitle, newContent);
            PostResponse post = postService.updatePost(id, request);
            return ApiResponse.success(PostSuccessCode.POST_UPDATED, post);
        } catch (PostNotFoundException e) {
            return ApiResponse.error(e.getMessage());
        } catch (BaseException e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    // DELETE /posts/{id} 📝 과제
    public ApiResponse<Void> deletePost(Long id) {
        // TODO: postService.deletePost() 호출, 예외 발생 시 에러 메시지 출력
        try {
            postService.deletePost(id);
            return ApiResponse.success(PostSuccessCode.POST_DELETED);
        } catch (BaseException e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}