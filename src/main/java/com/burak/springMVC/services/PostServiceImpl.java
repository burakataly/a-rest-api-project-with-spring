package com.burak.springMVC.services;

import com.burak.springMVC.entities.Post;
import com.burak.springMVC.entities.User;
import com.burak.springMVC.repository.IPostRepository;
import com.burak.springMVC.requests.PostCreateRequest;
import com.burak.springMVC.requests.PostUpdateRequest;
import com.burak.springMVC.responses.PostResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {
    private IPostRepository postRepository;
    private IUserService userService;

    public PostServiceImpl(IPostRepository postRepository, IUserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Override
    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> posts = (userId.isPresent()) ? postRepository.findByUserId(userId.get()) :
                postRepository.findAll();
        if(posts == null) return null;
        List<PostResponse> responses = new ArrayList<>();
        for(Post post : posts){
            responses.add(new PostResponse(post));
        }
        return responses;
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public PostResponse getPostByIdWithLikes(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        return (post != null) ? new PostResponse(post) : null;
    }

    @Override
    public Post createPost(PostCreateRequest postCreateRequest) {
        User user = userService.getUserById(postCreateRequest.getUserId());
        if(user == null) return null;
        Post post = new Post();
        post.setText(postCreateRequest.getText());
        post.setTitle(postCreateRequest.getTitle());
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(PostUpdateRequest postUpdateRequest, Long postId) {
        Optional<Post> temp = postRepository.findById(postId);
        if(temp.isEmpty()) return null;
        Post foundPost = temp.get();
        foundPost.setText(postUpdateRequest.getText());
        foundPost.setTitle(postUpdateRequest.getTitle());
        return postRepository.save(foundPost);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
