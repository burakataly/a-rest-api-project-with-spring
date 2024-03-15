package com.burak.app.repository;

import com.burak.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
