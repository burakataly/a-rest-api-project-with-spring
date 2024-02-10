package com.burak.springMVC.services;

import com.burak.springMVC.entities.Like;
import com.burak.springMVC.requests.LikeCreateRequest;
import com.burak.springMVC.responses.LikeResponse;

import java.util.List;

public interface ILikeService {
    List<LikeResponse> getLikesByForeignKeys(Long userId, Long postId);
    Like getLikeById(Long likeId);
    Like createLike(LikeCreateRequest likeCreateRequest);
    void deleteLike(Long likeId);
}
