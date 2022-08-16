package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository  extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByEmail(String email);

}
