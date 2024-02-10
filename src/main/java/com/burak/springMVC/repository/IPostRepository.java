package com.burak.springMVC.repository;

import com.burak.springMVC.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
}
