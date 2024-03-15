package com.burak.questApp.repository;

import com.burak.questApp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    @Query(value = "SELECT id FROM post WHERE user_id = :userId ORDER BY date DESC LIMIT 5", nativeQuery = true)
    List<Long> findTopByUsedId(Long userId);
}
