package com.burak.questApp.repository;

import com.burak.questApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
