package com.burak.springMVC.services;

import com.burak.springMVC.entities.User;
import com.burak.springMVC.repository.IUserRepository;
import com.burak.springMVC.requests.UserRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User updateUser(Long userId, UserRequest userRequest) {
        Optional<User> temp = userRepository.findById(userId);
        if(temp.isPresent()){
            User foundUser = temp.get();
            foundUser.setUsername(userRequest.getUsername());
            foundUser.setPassword(userRequest.getPassword());
            return userRepository.save(foundUser);
        }
        else return null;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
