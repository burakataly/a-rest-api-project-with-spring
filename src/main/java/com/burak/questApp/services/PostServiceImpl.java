package com.burak.questApp.services;

import com.burak.questApp.entities.Post;
import com.burak.questApp.entities.User;
import com.burak.questApp.repository.IPostRepository;
import com.burak.questApp.requests.PostCreateRequest;
import com.burak.questApp.requests.PostUpdateRequest;
import com.burak.questApp.responses.PostResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {
    private final IPostRepository postRepository;
    private final IUserService userService;

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
        Post post = Post.builder().
                text(postCreateRequest.getText()).
                title(postCreateRequest.getTitle()).
                user(user).
                date(new Date())
                .build();
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
