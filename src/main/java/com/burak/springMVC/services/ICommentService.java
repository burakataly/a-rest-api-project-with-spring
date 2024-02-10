package com.burak.springMVC.services;

import com.burak.springMVC.entities.Comment;
import com.burak.springMVC.requests.CommentCreateRequest;
import com.burak.springMVC.requests.CommentUpdateRequest;

import java.util.List;


public interface ICommentService {

    List<Comment> getCommentsByForeignKeys(Long userId, Long postId);

    Comment getCommentById(Long commentId);

    Comment createComment(CommentCreateRequest commentCreateRequest);

    Comment updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest);

    void deleteComment(Long commentId);
}
