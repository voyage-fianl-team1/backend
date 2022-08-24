package com.sparta.matchgi.service;

import com.sparta.matchgi.auth.jwt.JwtDecoder;
import com.sparta.matchgi.dto.SendChatRequestDto;
import com.sparta.matchgi.dto.ChatResponseDto;
import com.sparta.matchgi.model.Chat;
import com.sparta.matchgi.model.Room;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.repository.ChatRepository;
import com.sparta.matchgi.repository.RoomRepository;
import com.sparta.matchgi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final JwtDecoder jwtDecoder;

    private final UserRepository userRepository;

    public ResponseEntity<?> sendChat(Long roomId, SendChatRequestDto sendChatRequestDto,String token) {

        String email = jwtDecoder.decodeEmail(token);

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        Room room = roomRepository.findById(roomId).orElseThrow(
                ()-> new IllegalArgumentException("일치하는 채팅방이 없습니다.")
        );

        Chat chat = new Chat(room, sendChatRequestDto.getMessage(),user);

        chatRepository.save(chat);

        ChatResponseDto chatResponseDto = new ChatResponseDto(chat);

        return new ResponseEntity<>(chatResponseDto,HttpStatus.valueOf(200));

    }

    public ResponseEntity<?> showChats(Long roomId,Long lastChat, int limit) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                ()-> new IllegalArgumentException("일치하는 채팅방이 없습니다")
        );

        Pageable pageable =Pageable.ofSize(limit);

        Slice<ChatResponseDto> chatResponseDtoList;

        if(lastChat == -1)
        {
            chatResponseDtoList = chatRepository.showChatsFirst(room,pageable);
        }else{
            chatResponseDtoList = chatRepository.showChatsAfter(room,lastChat,pageable);
        }


        return new ResponseEntity<>(chatResponseDtoList,HttpStatus.valueOf(200));

    }
}
