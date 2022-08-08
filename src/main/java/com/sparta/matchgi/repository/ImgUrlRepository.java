package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgUrlRepository extends JpaRepository<ImgUrl,Long> {
}
