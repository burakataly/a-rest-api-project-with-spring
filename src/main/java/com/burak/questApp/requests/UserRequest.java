package com.burak.questApp.requests;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private Integer avatar;
}
