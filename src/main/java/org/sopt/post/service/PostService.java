package org.sopt.post.service;

import org.sopt.post.domain.Post;
import org.sopt.post.dto.response.PostListResponse;
import org.sopt.user.domain.User;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(UserNotFoundException::new);
        Post post = new Post(
                request.title(),
                request.content(),
                user
        );
        postRepository.saveAndFlush(post);
        return PostResponse.from(post);
    }

    @Transactional(readOnly = true)
    public List<PostListResponse> readAllPosts() {
        /*
        return postRepository.findAllWithUserAndLikes().stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());

         */
        return postRepository.findAllWithLikeCount();
    }

    @Transactional(readOnly = true)
    public PostResponse readPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return PostResponse.from(post);
    }

    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        post.update(request.title(), request.content());
        return PostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        post.softDelete();
    }

    @Transactional(readOnly = true)
    public List<PostListResponse> searchByTitle(String keyword) {
        return postRepository.searchByTitle(keyword);
    }
}