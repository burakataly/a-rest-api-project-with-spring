package com.burak.questApp.responses;

import com.burak.questApp.entities.Post;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String text;
    private Long userId;
    private String username;
    private List<LikeResponse> postLikes;
    private Date date;
    //to map post object to postResponse object
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.text = post.getText();
        this.userId = post.getUser().getId();
        this.username = post.getUser().getUsername();
        this.date = post.getDate();
        this.postLikes = post.getPostLikes().stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }
}
