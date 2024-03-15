package com.burak.app.services;

import com.burak.app.entities.Post;

import com.burak.app.requests.PostCreateRequest;
import com.burak.app.requests.PostUpdateRequest;
import com.burak.app.responses.PostResponse;

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
