package com.burak.questApp.services;

import com.burak.questApp.entities.Post;

import com.burak.questApp.requests.PostCreateRequest;
import com.burak.questApp.requests.PostUpdateRequest;
import com.burak.questApp.responses.PostResponse;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    List<PostResponse> getAllPosts(Optional<Long> userId);
    Post getPostById(Long postId);
    PostResponse getPostByIdWithLikes(Long postId);
    Post createPost(PostCreateRequest postCreateRequest);
    Post updatePost(PostUpdateRequest postUpdateRequest, Long postId);
    void deletePost(Long postId);
}
