package com.burak.springMVC.services;

import com.burak.springMVC.entities.User;
import com.burak.springMVC.requests.UserRequest;

import java.util.List;


public interface IUserService {
    List<User> getAllUsers();
    User getUserById(Long userId);
    User createUser(UserRequest userRequest);
    User updateUser(Long userId, UserRequest userRequest);
    void deleteUser(Long userId);
}
