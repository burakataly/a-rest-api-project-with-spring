package com.burak.app.services;

import com.burak.app.entities.Like;
import com.burak.app.entities.Post;
import com.burak.app.entities.User;
import com.burak.app.repository.ILikeRepository;
import com.burak.app.requests.LikeCreateRequest;
import com.burak.app.responses.LikeResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LikeServiceImpl implements ILikeService {
    private final ILikeRepository likeRepository;
    private final IUserService userService;
    private final IPostService postService;

    public LikeServiceImpl(ILikeRepository likeRepository, IUserService userService, IPostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public List<LikeResponse> getLikesByForeignKeys(Long userId, Long postId) {
        List<Like> likes = likeRepository.findByForeignKeys(userId, postId);
        List<LikeResponse> responses = new ArrayList<>();
        for(Like like : likes){
            responses.add(new LikeResponse(like));
        }
        return responses;
    }

    @Override
    public Like getLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElseThrow(() -> new EntityNotFoundException("invalid likeId"));
    }

    @Override
    public Like createLike(LikeCreateRequest likeCreateRequest) {
        User user = userService.getUserById(likeCreateRequest.getUserId());
        Post post = postService.getPostById(likeCreateRequest.getPostId());
        if(user == null || post == null) throw new EntityNotFoundException("Invalid postId or userId");
        Like like = Like.builder().
                user(user).
                post(post).
                date(new Date()).
                build();
        return likeRepository.save(like);
    }

    @Override
    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
