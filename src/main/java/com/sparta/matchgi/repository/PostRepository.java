package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.Request;
import com.sparta.matchgi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {


    Post findPostById(Long postId);
    List<Post> findAllByUser(User user);
}
