package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.ShowRoomResponseDto;
import com.sparta.matchgi.model.User;

import java.util.List;

public interface UserRoomRepositoryCustom {

    List<ShowRoomResponseDto> ShowRoomPost(User user1);

}
