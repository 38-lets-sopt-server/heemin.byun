package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.response.CreatePostResponse;
import org.sopt.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostService {
    private List<Post> postList = new ArrayList<>(); // 임시 저장소 (나중에 DB로 교체됨)
    private Long nextId = 1L;

    private final PostRepository postRepository = new PostRepository();

    // CREATE ✅ 같이 구현
    // 글쓰기 화면에서 "완료" 버튼을 누르면 이 메서드가 호출돼요
    public CreatePostResponse createPost(CreatePostRequest request) {
        if (request.title == null || request.title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다!");
        }
        if (request.content == null || request.content.isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다!");
        }
        String createdAt = java.time.LocalDateTime.now().toString();
        Post post = new Post(postRepository.generateId(), request.title, request.content, request.author, createdAt);
        postRepository.save(post);
        return new CreatePostResponse(post.getId(), "게시글 등록 완료!");
    }

    // READ - 전체 📝 과제
    // 자유게시판 목록 화면에서 호출돼요
    public void readAllPosts() {
        // TODO: postList가 비어있으면 "등록된 게시글이 없습니다." 출력
        // TODO: 비어있지 않으면 모든 게시글의 getInfo()를 순서대로 출력
        List<Post> postList= postRepository.findAll();
        if(postList.isEmpty()){
            throw new IllegalArgumentException("등록된 게시물이 없습니다!");
        }


    }

    // READ - 단건 📝 과제
    // 목록에서 특정 게시글을 탭하면 호출돼요 (게시글 상세 화면)
    public Optional<Post> readPost(Long id) {
        // TODO: postList에서 id가 일치하는 게시글을 찾아 getInfo() 출력
        // TODO: 없으면 "해당 게시글을 찾을 수 없습니다." 출력
        Optional<Post> post = Optional.ofNullable(postRepository.findById(id));
        if (!post.isPresent()) {
            throw new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.!");
        }
        return post;
    }

    // UPDATE 📝 과제
    // 게시글 수정 화면에서 "완료"를 누르면 호출돼요
    public void updatePost(Long id, String newTitle, String newContent) {
        // TODO: postList에서 id가 일치하는 게시글을 찾아 update() 호출
        // TODO: 없으면 "해당 게시글을 찾을 수 없습니다." 출력
    }

    // DELETE 📝 과제
    // 게시글 상세에서 삭제를 누르면 호출돼요
    public void deletePost(Long id) {
        // TODO: postList에서 id가 일치하는 게시글을 제거
        // TODO: 성공하면 "삭제 완료!", 없으면 "해당 게시글을 찾을 수 없습니다." 출력
    }
}