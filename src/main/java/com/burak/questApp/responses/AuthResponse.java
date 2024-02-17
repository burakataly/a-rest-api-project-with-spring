package com.burak.questApp.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String message;
    private Long userId;
    private String accessToken;
    private String refreshToken;
}
