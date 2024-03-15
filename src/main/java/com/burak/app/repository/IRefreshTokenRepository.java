package com.burak.questApp.repository;

import com.burak.questApp.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserId(Long userId);
}
