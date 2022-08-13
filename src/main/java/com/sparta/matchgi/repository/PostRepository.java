package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {


    Post findPostById(Long id);

}
