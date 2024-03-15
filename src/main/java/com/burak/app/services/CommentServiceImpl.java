package com.burak.app.services;

import com.burak.app.entities.Comment;
import com.burak.app.entities.Post;
import com.burak.app.entities.User;
import com.burak.app.repository.ICommentRepository;
import com.burak.app.requests.CommentCreateRequest;
import com.burak.app.requests.CommentUpdateRequest;
import com.burak.app.responses.CommentResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {
    private final ICommentRepository commentRepository;
    private final IUserService userService;
    private final IPostService postService;

    public CommentServiceImpl(ICommentRepository commentRepository, IUserService userService,
                              IPostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public List<CommentResponse> getCommentsByForeignKeys(Long userId, Long postId) {
        List<Comment> comments = commentRepository.findByForeignKeys(userId, postId);
        List<CommentResponse> responses = new ArrayList<>();
        for(Comment comment : comments){
            responses.add(new CommentResponse(comment));
        }
        return responses;
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("invalid commentId"));
    }

    @Override
    public Comment createComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getUserById(commentCreateRequest.getUserId());
        Post post = postService.getPostById(commentCreateRequest.getPostId());
        if(user == null || post == null) throw new EntityNotFoundException("Invalid postId or userId");
        Comment comment = Comment.builder().
                user(user).
                post(post).
                text(commentCreateRequest.getText()).
                date(new Date()).
                build();
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> temp = commentRepository.findById(commentId);
        if(temp.isEmpty()) throw new EntityNotFoundException("Invalid commentId");
        Comment comment = temp.get();
        comment.setText(commentUpdateRequest.getText());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
