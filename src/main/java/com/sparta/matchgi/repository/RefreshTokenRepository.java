package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository  extends JpaRepository<RefreshToken,Long> {

}
