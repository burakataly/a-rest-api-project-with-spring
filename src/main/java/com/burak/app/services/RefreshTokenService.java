package com.burak.questApp.services;

import com.burak.questApp.entities.RefreshToken;
import com.burak.questApp.entities.User;
import com.burak.questApp.repository.IRefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${refresh.expires.in}")
    private Long EXPIRES_IN;
    private final IRefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(IRefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createRefreshToken(User user){
        RefreshToken token = findByUserId(user.getId());
        if(token == null){
            token = RefreshToken.builder().
                    user(user).
                    build();
        }
        token.setToken(UUID.randomUUID().toString());
        token.setExpireDate(Date.from(Instant.now().plusSeconds(EXPIRES_IN)));
        refreshTokenRepository.save(token);
        return token.getToken();
    }

    public Boolean isTokenExpired(RefreshToken token){
        return token.getExpireDate().before(new Date());
    }

    public RefreshToken findByUserId(Long userId){
        return refreshTokenRepository.findByUserId(userId);
    }
}
