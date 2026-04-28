package org.sopt.post.service;

import org.sopt.post.domain.Post;
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
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 글쓰기 화면에서 "완료" 버튼을 누르면 이 메서드가 호출돼요
    @Transactional
    public PostResponse createPost(CreatePostRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException());
        Post post = new Post(
                request.title(),
                request.content(),
                user
        );
        postRepository.save(post);
        return PostResponse.from(post);
    }

    // 자유게시판 목록 화면에서 호출돼요
    @Transactional(readOnly = true)
    public List<PostResponse> readAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

    // 목록에서 특정 게시글을 탭하면 호출돼요 (게시글 상세 화면)
    @Transactional(readOnly = true)
    public PostResponse readPost(Long id) {
        // TODO: postList에서 id가 일치하는 게시글을 찾아 getInfo() 출력
        // TODO: 없으면 "해당 게시글을 찾을 수 없습니다." 출력
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException());
        return PostResponse.from(post);
    }

    // 게시글 수정 화면에서 "완료"를 누르면 호출돼요
    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest request) {
        // TODO: postList에서 id가 일치하는 게시글을 찾아 update() 호출
        // TODO: 없으면 "해당 게시글을 찾을 수 없습니다." 출력
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException());
        post.update(request.title(), request.content());
        return PostResponse.from(post);
    }


    /*
    // 게시글 상세에서 삭제를 누르면 호출돼요
    public void deletePost(Long id) {

        boolean deleted = postRepository.deleteBy
        if (!deleted) {
            throw new PostNotFoundException();
        }
    }
    */
}