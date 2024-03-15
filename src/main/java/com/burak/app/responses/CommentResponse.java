package com.burak.app.responses;

import com.burak.app.entities.Comment;
import lombok.Data;
@Data
public class CommentResponse {
    private Long id;
    private Long userId;
    private String username;
    private String text;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.username = comment.getUser().getUsername();
        this.text = comment.getText();
    }
}
