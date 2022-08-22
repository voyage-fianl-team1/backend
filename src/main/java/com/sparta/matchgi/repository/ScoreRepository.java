package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.PersonalRankingResponseDto;
import com.sparta.matchgi.model.Score;
import com.sparta.matchgi.model.SubjectEnum;
import com.sparta.matchgi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ScoreRepository  extends JpaRepository<Score,Long> {

    Optional<Score> findByUserAndSubject(User user, SubjectEnum subject);

    @Query("SELECT new com.sparta.matchgi.dto.PersonalRankingResponseDto(u.nickname,s.subject,u.profileImgUrl,s.win) " +
            "FROM Score s " +
            "join s.user u " +
            "where s.subject = :subject " +
            "order by s.win DESC")
    Slice<PersonalRankingResponseDto> findByPersonalRanking(Pageable pageable,SubjectEnum subject);

    Optional<Score> findByUser(User user);


}
