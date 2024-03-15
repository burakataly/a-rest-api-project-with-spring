package com.burak.app.repository;

import com.burak.app.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    @Query(value = "SELECT id FROM post WHERE user_id = :userId ORDER BY date DESC LIMIT 5", nativeQuery = true)
    List<Long> findTopByUsedId(Long userId);
}
