package com.burak.questApp.services;

import com.burak.questApp.entities.Comment;
import com.burak.questApp.entities.Post;
import com.burak.questApp.entities.User;
import com.burak.questApp.repository.ICommentRepository;
import com.burak.questApp.requests.CommentCreateRequest;
import com.burak.questApp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements ICommentService {
    private ICommentRepository commentRepository;
    private IUserService userService;
    private IPostService postService;

    public CommentServiceImpl(ICommentRepository commentRepository, IUserService userService,
                              IPostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public List<Comment> getCommentsByForeignKeys(Long userId, Long postId) {
        return commentRepository.findByForeignKeys(userId, postId);
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public Comment createComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getUserById(commentCreateRequest.getUserId());
        Post post = postService.getPostById(commentCreateRequest.getPostId());
        if(user == null || post == null) return null;
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText(commentCreateRequest.getText());
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> temp = commentRepository.findById(commentId);
        if(temp.isEmpty()) return null;
        Comment comment = temp.get();
        comment.setText(commentUpdateRequest.getText());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
