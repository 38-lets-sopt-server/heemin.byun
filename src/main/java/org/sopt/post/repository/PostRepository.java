package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private final Map<Long, Post> postMap = new HashMap<>();
    private Long nextId = 1L;

    public Post save(Post post) {
        postMap.put(post.getId(), post);
        return post;
    }

    public List<Post> findAll() {
        return postMap.values().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public Post findById(Long id) {
        return postMap.get(id);
    }

    public boolean deleteById(Long id) {
        return postMap.remove(id) != null;
    }

    public Long generateId() {
        return nextId++;
    }
}