package com.burak.questApp.controller;

import com.burak.questApp.entities.Post;
import com.burak.questApp.requests.PostCreateRequest;
import com.burak.questApp.requests.PostUpdateRequest;
import com.burak.questApp.responses.PostResponse;
import com.burak.questApp.services.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final IPostService postService;

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
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequest postCreateRequest){
        return (postService.createPost(postCreateRequest) != null) ? new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@RequestBody PostUpdateRequest postUpdateRequest, @PathVariable Long postId){
        return (postService.updatePost(postUpdateRequest, postId) != null) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }
}
