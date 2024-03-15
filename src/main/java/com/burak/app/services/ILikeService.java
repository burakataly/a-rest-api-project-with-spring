package com.burak.app.services;

import com.burak.app.entities.Like;
import com.burak.app.requests.LikeCreateRequest;
import com.burak.app.responses.LikeResponse;

import java.util.List;

public interface ILikeService {
    List<LikeResponse> getLikesByForeignKeys(Long userId, Long postId);
    Like getLikeById(Long likeId);
    Like createLike(LikeCreateRequest likeCreateRequest);
    void deleteLike(Long likeId);
}
