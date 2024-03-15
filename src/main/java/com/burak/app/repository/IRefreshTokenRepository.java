package com.burak.app.repository;

import com.burak.app.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserId(Long userId);
}
