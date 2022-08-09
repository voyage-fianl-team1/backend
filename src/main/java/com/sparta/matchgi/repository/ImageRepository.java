package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.ImgUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImgUrl,Long> {

}
