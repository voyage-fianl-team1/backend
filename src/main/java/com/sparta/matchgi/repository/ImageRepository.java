package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.ImgUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImgUrl,Long> {


}
