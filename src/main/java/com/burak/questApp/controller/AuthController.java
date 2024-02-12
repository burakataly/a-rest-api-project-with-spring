package com.burak.questApp.controller;

import com.burak.questApp.requests.UserRequest;
import com.burak.questApp.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(@RequestBody UserRequest userRequest){
        return authService.login(userRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest userRequest){
        return authService.register(userRequest);
    }
}