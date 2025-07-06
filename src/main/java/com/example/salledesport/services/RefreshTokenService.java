package com.example.salledesport.services;


import com.example.salledesport.model.RefreshToken;
import com.example.salledesport.repositories.RefreshTokenRepository;
import com.example.salledesport.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value ("${mariem.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;


    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId){
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());


        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }


    public void deleteByUserId(Long userId) {
        try {
            refreshTokenRepository.deleteByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting refresh tokens for user ID: " + userId, e);
        }
    }
}
