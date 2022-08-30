package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImgUrl,Long> {

    ImgUrl findImgUrlById(Long id);

    ImgUrl findImgUrlByPath(String path);

    List<ImgUrl> findAllByPost(Post post);
}
