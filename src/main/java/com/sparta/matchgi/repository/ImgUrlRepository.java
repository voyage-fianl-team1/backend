package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.ImagePathDto;
import com.sparta.matchgi.dto.ImageUrlDto;
import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgUrlRepository extends JpaRepository<ImgUrl,Long> {

    ImgUrl findImgUrlByPath(String path);
    List<ImgUrl> findByPostId(Long postId);

}
