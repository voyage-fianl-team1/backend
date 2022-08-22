package com.sparta.matchgi.service;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.*;
import com.sparta.matchgi.model.Room;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.repository.UserRoomRepository;
import com.sparta.matchgi.repository.UserRoomRepositoryImpl;
import com.sparta.matchgi.util.converter.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserRoomService {

    private final UserRoomRepositoryImpl userRoomRepositoryImpl;

    private final UserRoomRepository userRoomRepository;

    public ResponseEntity<?> showUserRoom(Long lastActive, UserDetailsImpl userDetails) {

        LocalDateTime lastActiveLocalDateTime = DateConverter.millsToLocalDateTime(lastActive);

        User user = userDetails.getUser();

        List<ShowRoomResponseDto> showRoomResponseDtoList = userRoomRepositoryImpl.ShowRoomPost(user,lastActiveLocalDateTime);

        return new ResponseEntity<>(showRoomResponseDtoList, HttpStatus.valueOf(200));

    }
}



