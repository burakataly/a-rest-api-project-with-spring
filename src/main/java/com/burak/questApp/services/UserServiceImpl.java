package com.burak.questApp.services;

import com.burak.questApp.entities.User;
import com.burak.questApp.repository.IUserRepository;
import com.burak.questApp.requests.UserRequest;
import jakarta.persistence.EntityNotFoundException;
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
        User user = userRepository.findByUsername(userRequest.getUsername());
        if(user != null) return null;
        user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
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
