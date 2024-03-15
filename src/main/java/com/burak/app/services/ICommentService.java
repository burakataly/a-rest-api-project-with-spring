package com.burak.app.services;

import com.burak.app.entities.Comment;
import com.burak.app.requests.CommentCreateRequest;
import com.burak.app.requests.CommentUpdateRequest;
import com.burak.app.responses.CommentResponse;

import java.util.List;


public interface ICommentService {

    List<CommentResponse> getCommentsByForeignKeys(Long userId, Long postId);

    Comment getCommentById(Long commentId);

    Comment createComment(CommentCreateRequest commentCreateRequest);

    Comment updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest);

    void deleteComment(Long commentId);
}
