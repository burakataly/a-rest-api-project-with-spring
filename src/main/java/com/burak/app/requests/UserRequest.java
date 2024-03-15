package com.burak.questApp.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    private String username;
    private String password;
    private Integer avatar;
}
