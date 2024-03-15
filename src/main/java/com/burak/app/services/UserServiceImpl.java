package com.burak.app.services;

import com.burak.app.entities.User;
import com.burak.app.repository.ICommentRepository;
import com.burak.app.repository.ILikeRepository;
import com.burak.app.repository.IPostRepository;
import com.burak.app.repository.IUserRepository;
import com.burak.app.requests.UserRequest;
import com.burak.app.responses.UserResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(IUserRepository userRepository, IPostRepository postRepository,
                           ICommentRepository commentRepository, ILikeRepository likeRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.passwordEncoder = passwordEncoder;
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
        if(user != null) throw new EntityExistsException("There is already a user with this username.");
        user = User.builder().
                username(userRequest.getUsername()).
                password(passwordEncoder.encode(userRequest.getPassword())).
                avatar(userRequest.getAvatar()).
                build();
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("invalid userId"));
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
            foundUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            foundUser.setAvatar(userRequest.getAvatar());
            return userRepository.save(foundUser);
        }
        else throw new EntityNotFoundException("Invalid userId");
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
