package com.burak.questApp.services;

import com.burak.questApp.entities.User;
import com.burak.questApp.requests.UserRequest;
import com.burak.questApp.responses.UserResponse;

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
