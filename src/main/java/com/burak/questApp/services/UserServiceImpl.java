package com.burak.questApp.services;

import com.burak.questApp.entities.User;
import com.burak.questApp.repository.ICommentRepository;
import com.burak.questApp.repository.ILikeRepository;
import com.burak.questApp.repository.IPostRepository;
import com.burak.questApp.repository.IUserRepository;
import com.burak.questApp.requests.UserRequest;
import com.burak.questApp.responses.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final ICommentRepository commentRepository;
    private final ILikeRepository likeRepository;

    public UserServiceImpl(IUserRepository userRepository, IPostRepository postRepository,
                           ICommentRepository commentRepository, ILikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();
        for(User user : users){
            responses.add(new UserResponse(user));
        }
        return responses;
    }

    @Override
    public User createUser(UserRequest userRequest) {
        User user = userRepository.findByUsername(userRequest.getUsername());
        if(user != null) return null;
        user = User.builder().
                username(userRequest.getUsername()).
                password(userRequest.getPassword()).
                avatar(userRequest.getAvatar()).
                build();
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
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
            foundUser.setAvatar(userRequest.getAvatar());
            return userRepository.save(foundUser);
        }
        else return null;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds = postRepository.findTopByUsedId(userId);
        if(postIds.isEmpty()) return null;
        List<Object> result = new ArrayList<>();
        result.addAll(commentRepository.findTopByPostIds(postIds));
        result.addAll(likeRepository.findTopByPostIds(postIds));
        return result;
    }
}
