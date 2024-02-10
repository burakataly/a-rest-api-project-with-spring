package com.burak.questApp.controller;

import com.burak.questApp.entities.Comment;
import com.burak.questApp.requests.CommentCreateRequest;
import com.burak.questApp.requests.CommentUpdateRequest;
import com.burak.questApp.services.ICommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getCommentsByForeignKeys(@RequestParam(required = false) Long userId,
                                                 @RequestParam(required = false) Long postId){
        return commentService.getCommentsByForeignKeys(userId, postId);
    }

    @GetMapping("/{commentId}")
    public Comment getCommentById(@PathVariable Long commentId){
        return commentService.getCommentById(commentId);
    }

    @PostMapping
    public Comment createComment(@RequestBody CommentCreateRequest commentCreateRequest){
        return commentService.createComment(commentCreateRequest);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long commentId,
                                 @RequestBody CommentUpdateRequest commentUpdateRequest){
        return commentService.updateComment(commentId, commentUpdateRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }
}
