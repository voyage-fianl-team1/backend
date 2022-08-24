package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.ShowRoomResponseDto;
import com.sparta.matchgi.model.Room;
import com.sparta.matchgi.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRoomRepositoryCustom {

    List<ShowRoomResponseDto> ShowRoomPost(User user1);

}
