package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.GetScoresResponseDto;
import com.sparta.matchgi.dto.ParticipationResponseDto;
import com.sparta.matchgi.dto.RequestResponseDto;
import com.sparta.matchgi.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Long> {
    Optional<Request> findByUserAndPost(User user, Post post);

    List<Request> findAllByPost(Post post);

    @Query("select new com.sparta.matchgi.dto.RequestResponseDto(r.id,u.nickname,r.requestStatus,u.profileImgUrl)" +
            " FROM Request r " +
            "join r.user u " +
            "join r.post p " +
            "WHERE p = :post AND r.requestStatus NOT in :requestStatus " +
            "ORDER BY r.id DESC ")
    List<RequestResponseDto> showParticipationList(Post post,RequestStatus requestStatus);

    List<Request> findAllByUser(User user);

    @Query("SELECT r FROM Request r join fetch r.post p left join fetch p.imageList join r.user u where u=:user ")
    List<Request> findAllByUser_fetchPostAndImageList(User user);


    @Query("SELECT r from Request r join fetch r.post join fetch r.user where r.id=:id")
    Optional<Request> findById_fetchUserAndPost(Long id);


}
