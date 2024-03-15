package com.burak.app.services;

import com.burak.app.entities.User;
import com.burak.app.requests.UserRequest;
import com.burak.app.responses.UserResponse;

import java.util.List;


public interface IUserService {
    List<UserResponse> getAllUsers();
    User getUserById(Long userId);
    User getUserByUsername(String username);
    User createUser(UserRequest userRequest);
    User updateUser(Long userId, UserRequest userRequest);
    void deleteUser(Long userId);
    List<Object> getUserActivity(Long userId);
}
