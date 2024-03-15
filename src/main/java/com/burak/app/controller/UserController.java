package com.burak.app.controller;

import com.burak.app.requests.UserRequest;
import com.burak.app.responses.UserResponse;
import com.burak.app.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId){
        return new UserResponse(userService.getUserById(userId));
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId){
        return userService.getUserActivity(userId);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRequest userRequest){
        return (userService.createUser(userRequest) != null) ? new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest){
        return (userService.updateUser(userId, userRequest) != null) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
