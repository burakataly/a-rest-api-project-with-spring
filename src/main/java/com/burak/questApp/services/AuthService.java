package com.burak.questApp.services;

import com.burak.questApp.requests.UserRequest;
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

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                       IUserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(UserRequest userRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("BURDAAAAAAAAA");
        return jwtTokenProvider.generateToken(userRequest.getUsername());
    }

    public ResponseEntity<String> register(UserRequest userRequest){
        if(userService.getUserByUsername(userRequest.getUsername()) == null){
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userService.createUser(userRequest);
            return new ResponseEntity<>("user is successfully added", HttpStatus.CREATED);
        }
        else return new ResponseEntity<>("there is already a user with that username.", HttpStatus.BAD_REQUEST);
    }
}
