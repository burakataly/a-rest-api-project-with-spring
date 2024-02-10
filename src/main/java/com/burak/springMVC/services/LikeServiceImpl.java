package com.burak.springMVC.services;

import com.burak.springMVC.entities.Like;
import com.burak.springMVC.entities.Post;
import com.burak.springMVC.entities.User;
import com.burak.springMVC.repository.ILikeRepository;
import com.burak.springMVC.requests.LikeCreateRequest;
import com.burak.springMVC.responses.LikeResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LikeServiceImpl implements ILikeService {
    private ILikeRepository likeRepository;
    private IUserService userService;
    private IPostService postService;

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
        return likeRepository.findById(likeId).orElse(null);
    }

    @Override
    public Like createLike(LikeCreateRequest likeCreateRequest) {
        User user = userService.getUserById(likeCreateRequest.getUserId());
        Post post = postService.getPostById(likeCreateRequest.getPostId());
        if(user == null || post == null) return null;
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        return likeRepository.save(like);
    }

    @Override
    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
