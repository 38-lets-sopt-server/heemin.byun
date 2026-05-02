package org.sopt.like.service;

import org.sopt.global.api.annotation.Retry;
import org.sopt.like.entity.Like;
import org.sopt.like.exception.AlreadyLikedException;
import org.sopt.post.exception.PostNotFoundException;
import org.sopt.user.exception.UserNotFoundException;
import org.sopt.like.exception.LikeNotFoundException;
import org.sopt.like.repository.LikeRepository;
import org.sopt.post.entity.Post;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.entity.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository,
                       PostRepository postRepository,
                       UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Retry
    @Transactional
    public void addLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new AlreadyLikedException();
        }
        likeRepository.save(new Like(user, post));
    }

    @Retry
    @Transactional
    public void cancelLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(LikeNotFoundException::new);

        likeRepository.delete(like);
    }
}