package com.burak.questApp.responses;

import lombok.Data;

@Data
public class AuthResponse {
    private String message;
    private Long userId;
}
