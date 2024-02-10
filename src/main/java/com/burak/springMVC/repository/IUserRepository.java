package com.burak.springMVC.repository;

import com.burak.springMVC.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<User, Long> {
}
