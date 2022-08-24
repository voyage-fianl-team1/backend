package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Room;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.model.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRoomRepository extends JpaRepository<UserRoom,Long> {

    Optional<UserRoom> findByUserAndRoom(User user, Room room);

    @Query("SELECT r FROM UserRoom ur join ur.room r join ur.user u WHERE u = :user")
    List<Room> findRoomList(User user);

}
