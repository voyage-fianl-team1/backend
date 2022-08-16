package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Score;
import com.sparta.matchgi.model.SubjectEnum;
import com.sparta.matchgi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreRepository  extends JpaRepository<Score,Long> {

    Optional<Score> findByUserAndSubject(User user, SubjectEnum subject);

}
