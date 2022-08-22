package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.ParticipationResponseDto;
import com.sparta.matchgi.dto.RequestResponseDto;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.Request;
import com.sparta.matchgi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Long> {
    Optional<Request> findByUserAndPost(User user, Post post);

    @Query("select new com.sparta.matchgi.dto.RequestResponseDto(r.id,u.nickname,r.requestStatus)" +
            " FROM Request r " +
            "join r.user u " +
            "join r.post p " +
            "WHERE p = :post " +
            "ORDER BY r.id DESC ")
    List<RequestResponseDto> showParticipationList(Post post);

    List<Request> findAllByUser(User user);

}
