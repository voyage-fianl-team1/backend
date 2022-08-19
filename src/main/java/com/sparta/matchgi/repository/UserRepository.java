package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.MyPageResponseDto;
import com.sparta.matchgi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByNickname (String nickname);

    @Query("SELECT new com.sparta.matchgi.dto.MyPageResponseDto(u.profileImgUrl,u.nickname,sum(s.win), sum(s.draw) ,sum(s.lose)) " +
            "FROM User u " +
            "left outer join Score s on s.user=:user WHERE u=:user group by u" ) //sum 사용 위해 group by
    MyPageResponseDto myRanking(User user);


}
