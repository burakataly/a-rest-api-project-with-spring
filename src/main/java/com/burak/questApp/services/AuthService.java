package com.burak.questApp.services;

import com.burak.questApp.entities.RefreshToken;
import com.burak.questApp.entities.User;
import com.burak.questApp.requests.AuthRequest;
import com.burak.questApp.requests.RefreshTokenRequest;
import com.burak.questApp.requests.UserRequest;
import com.burak.questApp.responses.AuthResponse;
import com.burak.questApp.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserService userService;
    private final RefreshTokenService refreshTokenService;


    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                       IUserService userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        if(doAuthenticate(authRequest)){
            return createToken("user is successfully logged in.",
                    userService.getUserByUsername(authRequest.getUsername()));
        }
        else{
            return new ResponseEntity<>(AuthResponse.builder().
                    message("Invalid username or password!!!").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<AuthResponse> register(AuthRequest authRequest){
        if(userService.getUserByUsername(authRequest.getUsername()) == null){
            User user = userService.createUser(UserRequest.builder().
                    username(authRequest.getUsername()).
                    password(authRequest.getPassword())
                    .build());
            if(doAuthenticate(authRequest)){
                return createToken("user is successfully registered.", user);
            }
            else{
                return new ResponseEntity<>(AuthResponse.builder().
                        message("authentication is failed after registration").build(), HttpStatus.UNAUTHORIZED);
            }
        }
        else{
            return new ResponseEntity<>(AuthResponse.builder().
                    message("there is already a user with this username.").build(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<AuthResponse> refresh(RefreshTokenRequest refreshTokenRequest) {
        User user = userService.getUserById(refreshTokenRequest.getUserId());
        if(user == null){
            return new ResponseEntity<>(AuthResponse.builder().
                    message("there is not a user with this userId.").build(), HttpStatus.BAD_REQUEST);
        }
        RefreshToken token = refreshTokenService.findByUserId(user.getId());
        if(token == null){
            return new ResponseEntity<>(AuthResponse.builder().
                    message("there is not a refresh token of this user.").build(), HttpStatus.BAD_REQUEST);
        }
        if(token.getToken().equals(refreshTokenRequest.getRefreshToken()) && !refreshTokenService.isTokenExpired(token)){
            return createToken("Token is successfully refreshed.", user);
        }else{
            return new ResponseEntity<>(AuthResponse.builder().
                    message("Refresh token is not valid.").build(), HttpStatus.UNAUTHORIZED);
        }
    }

    private ResponseEntity<AuthResponse> createToken(String message, User user){
        return new ResponseEntity<>(AuthResponse.builder().
                accessToken("Bearer " + jwtTokenProvider.generateToken(user.getUsername())).
                refreshToken(refreshTokenService.createRefreshToken(user)).
                userId(user.getId()).
                message(message).build(), HttpStatus.CREATED);
    }

    private Boolean doAuthenticate(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            return false;
        }
    }
}
