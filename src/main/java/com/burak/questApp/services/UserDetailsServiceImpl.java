package com.burak.questApp.services;

import com.burak.questApp.entities.User;
import com.burak.questApp.repository.IUserRepository;
import com.burak.questApp.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private IUserRepository userRepository;

    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return JwtUserDetails.createJwtUserDetails(user);
    }

    public UserDetails loadUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return (user.isPresent()) ? JwtUserDetails.createJwtUserDetails(user.get()) : null;
    }
}
