package com.burak.questApp.controller;

import com.burak.questApp.entities.Comment;
import com.burak.questApp.requests.CommentCreateRequest;
import com.burak.questApp.requests.CommentUpdateRequest;
import com.burak.questApp.responses.CommentResponse;
import com.burak.questApp.services.ICommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getCommentsByForeignKeys(@RequestParam(required = false) Long userId,
                                                          @RequestParam(required = false) Long postId){
        return commentService.getCommentsByForeignKeys(userId, postId);
    }

    @GetMapping("/{commentId}")
    public CommentResponse getCommentById(@PathVariable Long commentId){
        return new CommentResponse(commentService.getCommentById(commentId));
    }

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentCreateRequest commentCreateRequest){
        return (commentService.createComment(commentCreateRequest) != null) ? new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId,
                                 @RequestBody CommentUpdateRequest commentUpdateRequest){
        return (commentService.updateComment(commentId, commentUpdateRequest) != null) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }
}
