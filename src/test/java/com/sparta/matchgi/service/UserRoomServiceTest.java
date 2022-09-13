package com.sparta.matchgi.service;

import com.sparta.matchgi.RedisRepository.RedisChatRepository;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.GetRoomUserResponseDto;
import com.sparta.matchgi.dto.ShowRoomResponseDto;
import com.sparta.matchgi.model.*;
import com.sparta.matchgi.repository.ChatRepository;
import com.sparta.matchgi.repository.RoomRepository;
import com.sparta.matchgi.repository.UserRoomRepository;
import com.sparta.matchgi.repository.UserRoomRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class UserRoomServiceTest {

    @Mock
    private UserRoomRepositoryImpl userRoomRepositoryImpl;

    @Mock
    private UserRoomRepository userRoomRepository;

    @Mock
    private RedisChatRepository redisChatRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private RoomRepository roomRepository;

    private static User user;

    private static UserDetailsImpl userDetails;

    private static Post post;

    @BeforeAll
    static void setup(){
        user = new User(1L,"email","password","nickname",null,false);

        userDetails = new UserDetailsImpl(user);

        post = new Post(1L,user,"title", LocalDateTime.now().plusDays(1),"content", SubjectEnum.BADMINTON,0.0,0.0,0,0, MatchStatus.ONGOING,"1234",null);
    }

    @Nested
    @DisplayName("참여한 채팅방 및 마지막채팅 보기")
    class showUserRoom{

        @Nested
        @DisplayName("참여한 채팅방 및 마지막 채팅 성공")
        class showUserRoomSuccess{

            @Test
            @DisplayName("참여한 채팅방 리스트 및 마지막 채팅 정보")
            void test1(){

                Room room = new Room(1L,user,post);
                List<Room> roomList = new ArrayList<>();
                roomList.add(room);

                RedisChat redisChat = new RedisChat("1","1",room,user,"message",LocalDateTime.now());
                List<RedisChat> redisChatList = new ArrayList<>();
                redisChatList.add(redisChat);

                ShowRoomResponseDto showRoomResponseDto =
                        new ShowRoomResponseDto(room.getId(),post.getId(),post.getTitle(),null,1L,redisChat.getMessage(),redisChat.getUser().getNickname(),redisChat.getCreatedAt(),1L);
                List<ShowRoomResponseDto> showRoomResponseDtoList = new ArrayList<>();
                showRoomResponseDtoList.add(showRoomResponseDto);

                when(userRoomRepository.findRoomList(user))
                        .thenReturn(roomList);
                when(redisChatRepository.findByRoomIdOrderByCreatedAt(room.getId().toString()))
                        .thenReturn(redisChatList);
                when(userRoomRepositoryImpl.ShowRoomPost(user))
                        .thenReturn(showRoomResponseDtoList);

                UserRoomService userRoomService = new UserRoomService(userRoomRepositoryImpl,userRoomRepository,redisChatRepository,chatRepository,roomRepository);

                List<ShowRoomResponseDto> response = userRoomService.showUserRoom(userDetails).getBody();
                ShowRoomResponseDto responseDto = response.get(0);

                assertEquals(1L,response.size());
                assertEquals(room.getId(),responseDto.getRoomId());
                assertEquals(post.getId(),responseDto.getPostId());
                assertEquals(post.getTitle(),responseDto.getTitle());
                assertNull(responseDto.getImgUrl());
                assertEquals(1L,responseDto.getChatId());
                assertEquals(redisChat.getMessage(),responseDto.getMessage());
                assertEquals(redisChat.getUser().getNickname(),responseDto.getNickname());
                assertEquals(redisChat.getCreatedAt(),responseDto.getCreatedAt());
                assertEquals(1L,responseDto.getUnreadMessageCount());

            }
        }
    }

    @Nested
    @DisplayName("마지막 활동 시간 변경")
    class updateLastActive{

        @Nested
        @DisplayName("마지막 활동 시간 변경 실패")
        class updateLastActiveFailed{

            @Test
            @DisplayName("없는 채팅방")
            void test2(){
                Long roomId = 1L;

                UserRoomService userRoomService = new UserRoomService(userRoomRepositoryImpl,userRoomRepository,redisChatRepository,chatRepository,roomRepository);

                Exception exception = assertThrows(IllegalArgumentException.class,()-> userRoomService.updateLastActive(roomId,userDetails));

                assertEquals("일치하는 채팅방이 없습니다.",exception.getMessage());

            }

            @Test
            @DisplayName("속해있지 않은 채팅방")
            void test3(){

                Long roomId = 1L;

                Room room = new Room(1L,user,post);

                UserRoomService userRoomService = new UserRoomService(userRoomRepositoryImpl,userRoomRepository,redisChatRepository,chatRepository,roomRepository);
                when(roomRepository.findById(roomId))
                        .thenReturn(Optional.of(room));

                Exception exception = assertThrows(IllegalArgumentException.class,()-> userRoomService.updateLastActive(roomId,userDetails));

                assertEquals("채팅방에 속해있지 않습니다.",exception.getMessage());

            }
        }

        @Nested
        @DisplayName("마지막 활동 시간 변경 성공")
        class updateLastActiveSuccess{

            @Test
            @DisplayName("마지막 활동시간 변경")
            void test4(){

                Long roomId = 1L;

                Room room = new Room(1L,user,post);

                UserRoom userRoom = new UserRoom(1L,user,room,LocalDateTime.now());

                UserRoomService userRoomService = new UserRoomService(userRoomRepositoryImpl,userRoomRepository,redisChatRepository,chatRepository,roomRepository);
                when(roomRepository.findById(roomId))
                        .thenReturn(Optional.of(room));
                when(userRoomRepository.findByUserAndRoom(user,room))
                        .thenReturn(Optional.of(userRoom));

                ResponseEntity<?> response = userRoomService.updateLastActive(roomId,userDetails);

                assertEquals(HttpStatus.OK,response.getStatusCode());

            }

        }
    }

    @Nested
    @DisplayName("채팅방에 속해있는 유저 정보")
    class getRoomUserList{

        @Nested
        @DisplayName("유저 정보 불러오기 실패")
        class getRoomUserListFailed{

            @Test
            @DisplayName("채팅방 없음")
            void test5(){

                Long roomId = 1L;

                UserRoomService userRoomService = new UserRoomService(userRoomRepositoryImpl,userRoomRepository,redisChatRepository,chatRepository,roomRepository);

                Exception exception = assertThrows(IllegalArgumentException.class,()-> userRoomService.getRoomUserList(roomId));

                assertEquals("일치하는 채팅방이 없습니다.",exception.getMessage());

            }

        }

        @Nested
        @DisplayName("유저 정보 불러오기 성공")
        class getRoomUserListSuccess{

            @Test
            @DisplayName("채팅방 안 유저 정보 불러오기")
            void test5(){

                Long roomId = 1L;

                Room room = new Room(1L,user,post);

                List<GetRoomUserResponseDto> getRoomUserResponseDtoList = new ArrayList<>();
                GetRoomUserResponseDto getRoomUserResponseDto = new GetRoomUserResponseDto(user.getId(), user.getProfileImgUrl(), user.getNickname());
                getRoomUserResponseDtoList.add(getRoomUserResponseDto);

                UserRoomService userRoomService = new UserRoomService(userRoomRepositoryImpl,userRoomRepository,redisChatRepository,chatRepository,roomRepository);
                when(roomRepository.findById(roomId))
                        .thenReturn(Optional.of(room));
                when(userRoomRepository.getRoomUserList(room))
                        .thenReturn(getRoomUserResponseDtoList);

                List<GetRoomUserResponseDto> response = userRoomService.getRoomUserList(roomId).getBody();

                GetRoomUserResponseDto responseDto = response.get(0);

                assertEquals(1L,response.size());
                assertEquals(user.getId(),responseDto.getUserId());
                assertEquals(user.getProfileImgUrl(),responseDto.getProfileImgUrl());
                assertEquals(user.getNickname(),responseDto.getNickname());

            }

        }

    }



}