package com.burak.app.requests;

import lombok.Data;

@Data
public class PostCreateRequest {
    private String title;
    private String text;
    private Long userId;
}
