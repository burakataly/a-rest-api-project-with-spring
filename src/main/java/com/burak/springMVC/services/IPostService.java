package com.burak.springMVC.services;

import com.burak.springMVC.entities.Post;

import com.burak.springMVC.requests.PostCreateRequest;
import com.burak.springMVC.requests.PostUpdateRequest;
import com.burak.springMVC.responses.PostResponse;

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
