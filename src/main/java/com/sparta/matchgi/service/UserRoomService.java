package com.sparta.matchgi.service;

import com.sparta.matchgi.RedisRepository.RedisChatRepository;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.ShowRoomResponseDto;
import com.sparta.matchgi.model.Chat;
import com.sparta.matchgi.model.RedisChat;
import com.sparta.matchgi.model.Room;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.repository.ChatRepository;
import com.sparta.matchgi.repository.RoomRepository;
import com.sparta.matchgi.repository.UserRoomRepository;
import com.sparta.matchgi.repository.UserRoomRepositoryImpl;
import com.sparta.matchgi.util.converter.DateConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoomService {

    private final UserRoomRepositoryImpl userRoomRepositoryImpl;

    private final UserRoomRepository userRoomRepository;
    private final RedisChatRepository redisChatRepository;

    private final ChatRepository chatRepository;


    public ResponseEntity<?> showUserRoom(Long lastActive, UserDetailsImpl userDetails) {

        List<Room> roomList = userRoomRepository.findRoomList(userDetails.getUser());

        for(Room room:roomList){

            List<RedisChat> redisChatList = redisChatRepository.findByRoomIdOrderByCreatedAt(room.getId().toString());

            List<Chat> chatList = redisChatList.stream().map(r->
                    new Chat(r.getRoom(),r.getMessage(),r.getUser(),r.getCreatedAt())
            ).collect(Collectors.toList());

            chatRepository.saveAll(chatList);

            redisChatRepository.deleteAll(redisChatList);
        }

        LocalDateTime lastActiveLocalDateTime = DateConverter.millsToLocalDateTime(lastActive);

        User user = userDetails.getUser();

        List<ShowRoomResponseDto> showRoomResponseDtoList = userRoomRepositoryImpl.ShowRoomPost(user,lastActiveLocalDateTime);

        return new ResponseEntity<>(showRoomResponseDtoList, HttpStatus.valueOf(200));

    }
}



