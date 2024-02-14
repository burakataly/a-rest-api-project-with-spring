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

    @Query(value = "SELECT 'commented on', c.post_id, u.username, u.avatar FROM comment c" +
            " LEFT JOIN user u ON c.user_id = u.id WHERE post_id IN :postIds ORDER BY date" +
            " DESC LIMIT 5", nativeQuery = true)
    List<Object> findTopByPostIds(List<Long> postIds);
}
