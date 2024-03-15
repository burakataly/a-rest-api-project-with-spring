package com.burak.app.responses;
import com.burak.app.entities.User;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private Integer avatar;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
    }
}
