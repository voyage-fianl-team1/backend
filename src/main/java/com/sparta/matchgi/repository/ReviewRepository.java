package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findByPost(Post post);
}
