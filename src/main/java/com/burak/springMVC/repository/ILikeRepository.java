package com.burak.springMVC.repository;

import com.burak.springMVC.entities.Comment;
import com.burak.springMVC.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE " +
            "(:userId IS NULL OR l.user.id = :userId) AND" +
            " (:postId IS NULL OR l.post.id = :postId)")
    List<Like> findByForeignKeys(Long userId, Long postId);
}
