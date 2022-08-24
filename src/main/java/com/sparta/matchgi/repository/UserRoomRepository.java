package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Room;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.model.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoom,Long> {

    Optional<UserRoom> findByUserAndRoom(User user, Room room);

}
