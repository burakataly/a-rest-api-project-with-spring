package com.burak.springMVC.controller;

import com.burak.springMVC.entities.Post;
import com.burak.springMVC.requests.PostCreateRequest;
import com.burak.springMVC.requests.PostUpdateRequest;
import com.burak.springMVC.responses.PostResponse;
import com.burak.springMVC.services.IPostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }

    @GetMapping("/{postId}")
    public PostResponse getPostById(@PathVariable Long postId){
        return postService.getPostByIdWithLikes(postId);
    }

    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest postCreateRequest){
        return postService.createPost(postCreateRequest);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@RequestBody PostUpdateRequest postUpdateRequest, @PathVariable Long postId){
        return postService.updatePost(postUpdateRequest, postId);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }
}
