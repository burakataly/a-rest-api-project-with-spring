package com.burak.app.repository;

import com.burak.app.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE " +
            "(:userId IS NULL OR l.user.id = :userId) AND" +
            " (:postId IS NULL OR l.post.id = :postId)")
    List<Like> findByForeignKeys(Long userId, Long postId);

    @Query(value = "SELECT 'liked', l.post_id, u.username, u.avatar FROM likes l" +
            " LEFT JOIN user u ON l.user_id = u.id WHERE post_id IN :postIds ORDER BY date" +
            " DESC LIMIT 5", nativeQuery = true)
    List<Object> findTopByPostIds(List<Long> postIds);
}
