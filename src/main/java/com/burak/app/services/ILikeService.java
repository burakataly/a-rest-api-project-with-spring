package com.burak.questApp.services;

import com.burak.questApp.entities.Like;
import com.burak.questApp.requests.LikeCreateRequest;
import com.burak.questApp.responses.LikeResponse;

import java.util.List;

public interface ILikeService {
    List<LikeResponse> getLikesByForeignKeys(Long userId, Long postId);
    Like getLikeById(Long likeId);
    Like createLike(LikeCreateRequest likeCreateRequest);
    void deleteLike(Long likeId);
}
