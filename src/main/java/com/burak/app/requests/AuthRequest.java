package com.burak.app.requests;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
