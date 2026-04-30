package org.sopt.post.repository;

import org.sopt.post.entity.Post;
import org.sopt.post.dto.response.PostListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT new org.sopt.post.dto.response.PostListResponse(" +
            "p.id, p.title, p.content, p.user.nickname, p.createdAt, COUNT(l)) " +
            "FROM Post p " +
            "LEFT JOIN Like l ON l.post = p " +
            "GROUP BY p.id, p.title, p.content, p.user.nickname, p.createdAt")
    List<PostListResponse> findAllWithLikeCount();

    @Query("SELECT new org.sopt.post.dto.response.PostListResponse(" +
            "p.id, p.title, p.content, p.user.nickname, p.createdAt, COUNT(l)) " +
            "FROM Post p " +
            "LEFT JOIN Like l ON l.post = p " +
            "WHERE p.title LIKE CONCAT('%', :keyword, '%') " +
            "GROUP BY p.id, p.title, p.content, p.user.nickname, p.createdAt")
    List<PostListResponse> searchByTitle(@Param("keyword") String keyword);
}