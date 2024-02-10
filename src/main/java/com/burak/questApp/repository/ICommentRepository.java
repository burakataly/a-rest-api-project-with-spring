package com.burak.questApp.repository;

import com.burak.questApp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE " +
    "(:userId IS NULL OR c.user.id = :userId) AND" +
            " (:postId IS NULL OR c.post.id = :postId)")
    List<Comment> findByForeignKeys(Long userId, Long postId);
}
