package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Banner;
import com.sparta.matchgi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner,Long> {
}
