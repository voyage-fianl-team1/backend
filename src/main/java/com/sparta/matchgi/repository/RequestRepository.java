package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.Request;
import com.sparta.matchgi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Long> {
    Optional<Request> findByUserAndPost(User user, Post post);

    List<Request> findAllByPost(Post post);

}
