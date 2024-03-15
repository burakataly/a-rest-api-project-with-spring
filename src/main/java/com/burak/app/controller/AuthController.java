package com.burak.app.controller;

import com.burak.app.requests.AuthRequest;
import com.burak.app.requests.RefreshTokenRequest;

import com.burak.app.responses.AuthResponse;
import com.burak.app.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest){
        return authService.login(authRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest){
        return authService.register(authRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return authService.refresh(refreshTokenRequest);
    }
}
