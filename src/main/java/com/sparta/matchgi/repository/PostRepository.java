package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {


    Post findPostById(Long id);

}
