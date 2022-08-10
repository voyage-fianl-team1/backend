package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    Post findPostById(Long postId);

}
