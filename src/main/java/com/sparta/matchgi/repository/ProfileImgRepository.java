package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.ProfileImg;
import com.sparta.matchgi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImgRepository extends JpaRepository<ProfileImg, Long> {
    Optional<ProfileImg> findByUser(User user);

}
