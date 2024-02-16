package com.burak.questApp.services;

import com.burak.questApp.entities.RefreshToken;
import com.burak.questApp.entities.User;
import com.burak.questApp.requests.RefreshTokenRequest;
import com.burak.questApp.requests.UserRequest;
import com.burak.questApp.responses.AuthResponse;
import com.burak.questApp.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private IUserService userService;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;


    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                       IUserService userService, PasswordEncoder passwordEncoder,
                       RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    public ResponseEntity<AuthResponse> login(UserRequest userRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return createToken("user is successfully logged in.",
                userService.getUserByUsername(userRequest.getUsername()));
    }

    public ResponseEntity<AuthResponse> register(UserRequest userRequest){
        if(userService.getUserByUsername(userRequest.getUsername()) == null){
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            User user = userService.createUser(userRequest);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return createToken("user is successfully registered.", user);
        }
        else{
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("there is already a user with this username.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<AuthResponse> refresh(RefreshTokenRequest refreshTokenRequest) {
        User user = userService.getUserById(refreshTokenRequest.getUserId());
        if(user == null){
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("there is not a user with this userId.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        RefreshToken token = refreshTokenService.findByUserId(user.getId());
        if(token == null){
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("there is not a refresh token of this user.");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        if(token.getToken().equals(refreshTokenRequest.getRefreshToken()) && !refreshTokenService.isTokenExpired(token)){
            return createToken("Token is successfully refreshed.", user);
        }else{
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Refresh token is not valid.");
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseEntity<AuthResponse> createToken(String message, User user){
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken("Bearer " + jwtTokenProvider.generateToken(user.getUsername()));
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());
        authResponse.setMessage(message);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }
}
