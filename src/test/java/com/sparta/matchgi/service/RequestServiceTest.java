package com.sparta.matchgi.service;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.ParticipationResponseDto;
import com.sparta.matchgi.dto.RequestResponseDto;
import com.sparta.matchgi.dto.UpdateRequestDto;
import com.sparta.matchgi.model.*;
import com.sparta.matchgi.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {
    @Mock
    PostRepository postRepository;

    @Mock
    RequestRepository requestRepository;

    @Mock
    ScoreRepository scoreRepository;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    RoomRepository roomRepository;

    @Mock
    UserRoomRepository userRoomRepository;

    @Mock
    SimpMessagingTemplate template;

    @Mock
    RequestRepositoryImpl requestRepositoryImpl;

    @Mock
    ChatRepository chatRepository;

    @Mock
    UserRepository userRepository;

    private static User user;

    private static User secondUser;

    private static UserDetailsImpl userDetails;

    private static UserDetailsImpl secondUserDetails;

    private static Post post;

    private static Post secondPost;

    @BeforeAll
    static void setup(){

        user = new User(1L,"email","password","nickname",null,false);

        userDetails = new UserDetailsImpl(user);

        post = new Post(1L,user,"title", LocalDateTime.now().plusDays(1),"content", SubjectEnum.BADMINTON,0.0,0.0,0,0, MatchStatus.ONGOING,"1234",null);

        secondUser = new User(2L,"email2","password2","nickname2",null,false);

        secondUserDetails = new UserDetailsImpl(secondUser);

        secondPost = new Post(2L,secondUser,"title", LocalDateTime.now().plusDays(1),"content", SubjectEnum.BADMINTON,0.0,0.0,0,0, MatchStatus.ONGOING,"1234",null);


    }

    @Nested
    @DisplayName("경기 참가신청")
    class RegisterMatch{
        @Nested
        @DisplayName("경기 참가신청 실패")
        class RegisterMatchFailed{

            @Test
            @DisplayName("없는 post")
            void test1(){

                Long postId = 1L;

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);

                Exception exception = assertThrows(IllegalArgumentException.class,()-> requestService.registerMatch(postId,userDetails));

                assertEquals("존재하지 않는 포스트입니다.",exception.getMessage());


            }

            @Test
            @DisplayName("본인의 경기에 참가신청")
            void test2(){
                Long postId = 1L;

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(postRepository.findById(postId))
                        .thenReturn(Optional.of(post));

                Exception exception = assertThrows(IllegalArgumentException.class,()-> requestService.registerMatch(postId,userDetails));

                assertEquals("본인이 쓴 글에는 참가신청 할 수 없습니다.",exception.getMessage());


            }

            @Test
            @DisplayName("참가신청 중복")
            void test3(){

                Long postId = 2L;

                Request request = new Request(1L,post,user,RequestStatus.PENDING);

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(postRepository.findById(postId))
                        .thenReturn(Optional.of(secondPost));
                when(requestRepository.findByUserAndPost(user,secondPost))
                        .thenReturn(Optional.of(request));

                Exception exception = assertThrows(IllegalArgumentException.class,()-> requestService.registerMatch(postId,userDetails));

                assertEquals("참가신청을 중복으로 할 수 없습니다.",exception.getMessage());


            }

            @Test
            @DisplayName("기한이 지난 경기에 참가신청")
            void test4(){
                Long postId = 3L;

                Post deadlineEndPost = post = new Post(3L,secondUser,"title", LocalDateTime.now().minusDays(1),"content", SubjectEnum.BADMINTON,0.0,0.0,0,0, MatchStatus.ONGOING,"1234",null);

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(postRepository.findById(postId))
                        .thenReturn(Optional.of(deadlineEndPost));
                when(requestRepository.findByUserAndPost(user,deadlineEndPost))
                        .thenReturn(Optional.empty());

                Exception exception = assertThrows(IllegalArgumentException.class,()-> requestService.registerMatch(postId,userDetails));

                assertEquals("기한이 지난 경기에는 참가신청 할 수 없습니다.",exception.getMessage());


            }


        }

        @Nested
        @DisplayName("경기참가신청 성공")
        class registerMatchSuccess{

            @Test
            @DisplayName("참가신청 성공")
            void test5(){
                ;
                Long postId = 2L;

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(postRepository.findById(postId))
                        .thenReturn(Optional.of(secondPost));
                when(requestRepository.findByUserAndPost(user,secondPost))
                        .thenReturn(Optional.empty());

                RequestResponseDto requestResponseDto = requestService.registerMatch(postId,userDetails).getBody();

                assertEquals(requestResponseDto.getNickname(),user.getNickname());
                assertEquals(requestResponseDto.getProfileImgUrl(),user.getProfileImgUrl());
                assertEquals(requestResponseDto.getStatus(),RequestStatus.PENDING);

            }
        }

    }


    @Nested
    @DisplayName("참가 신청 상태 변경")
    class updateRequest{
        @Nested
        @DisplayName("참가신청 상태 변경 실패")
        class updateRequestFailed{

            @Test
            @DisplayName("없는 request")
            void test6(){
                Long requestId = 1L;

                UpdateRequestDto updateRequestDto = new UpdateRequestDto(RequestStatus.ACCEPT);

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(requestRepository.findById_fetchUserAndPost(requestId))
                        .thenReturn(Optional.empty());

                Exception exception = assertThrows(IllegalArgumentException.class,()-> requestService.updateRequest(requestId,updateRequestDto,userDetails));

                assertEquals("존재하지 않는 request입니다",exception.getMessage());


            }

            @Test
            @DisplayName("권한이 없는 유저")
            void test7(){

                Long requestId = 1L;

                UpdateRequestDto updateRequestDto = new UpdateRequestDto(RequestStatus.ACCEPT);

                Request request = new Request(requestId,post,secondUser,RequestStatus.PENDING);

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(requestRepository.findById_fetchUserAndPost(requestId))
                        .thenReturn(Optional.of(request));

                Exception exception = assertThrows(IllegalArgumentException.class,()-> requestService.updateRequest(requestId,updateRequestDto,secondUserDetails));

                assertEquals("권한이 없습니다.",exception.getMessage());
            }


        }

        @Nested
        @DisplayName("참가신청 상태 변경 성공")
        class updateRequestSuccess{

            @Test
            @DisplayName("상태 변경 성공(REJECT)")
            void test8() {
                Long requestId = 1L;

                UpdateRequestDto updateRequestDto = new UpdateRequestDto(RequestStatus.REJECT);

                Request request = new Request(requestId,post,secondUser,RequestStatus.PENDING);

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(requestRepository.findById_fetchUserAndPost(requestId))
                        .thenReturn(Optional.of(request));


                RequestResponseDto requestResponseDto = requestService.updateRequest(requestId,updateRequestDto,userDetails).getBody();

                assertEquals(request.getId(),requestResponseDto.getRequestId());
                assertEquals(request.getUser().getNickname(),requestResponseDto.getNickname());
                assertEquals(updateRequestDto.getStatus(),requestResponseDto.getStatus());
                assertEquals(request.getUser().getProfileImgUrl(),requestResponseDto.getProfileImgUrl());

            }

            @Test
            @DisplayName("상태 변경 성공(WIN)")
            void test9() {
                Long requestId = 1L;

                UpdateRequestDto updateRequestDto = new UpdateRequestDto(RequestStatus.WIN);

                Request request = new Request(requestId,post,secondUser,RequestStatus.ACCEPT);

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(requestRepository.findById_fetchUserAndPost(requestId))
                        .thenReturn(Optional.of(request));


                RequestResponseDto requestResponseDto = requestService.updateRequest(requestId,updateRequestDto,userDetails).getBody();

                assertEquals(request.getId(),requestResponseDto.getRequestId());
                assertEquals(request.getUser().getNickname(),requestResponseDto.getNickname());
                assertEquals(updateRequestDto.getStatus(),requestResponseDto.getStatus());
                assertEquals(request.getUser().getProfileImgUrl(),requestResponseDto.getProfileImgUrl());

            }

            @Test
            @DisplayName("상태 변경 성공(LOSE)")
            void test10() {
                Long requestId = 1L;

                UpdateRequestDto updateRequestDto = new UpdateRequestDto(RequestStatus.LOSE);

                Request request = new Request(requestId,post,secondUser,RequestStatus.ACCEPT);

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(requestRepository.findById_fetchUserAndPost(requestId))
                        .thenReturn(Optional.of(request));


                RequestResponseDto requestResponseDto = requestService.updateRequest(requestId,updateRequestDto,userDetails).getBody();

                assertEquals(request.getId(),requestResponseDto.getRequestId());
                assertEquals(request.getUser().getNickname(),requestResponseDto.getNickname());
                assertEquals(updateRequestDto.getStatus(),requestResponseDto.getStatus());
                assertEquals(request.getUser().getProfileImgUrl(),requestResponseDto.getProfileImgUrl());

            }

            @Test
            @DisplayName("상태 변경 성공(DRAW)")
            void test11() {
                Long requestId = 1L;

                UpdateRequestDto updateRequestDto = new UpdateRequestDto(RequestStatus.DRAW);

                Request request = new Request(requestId,post,secondUser,RequestStatus.ACCEPT);

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(requestRepository.findById_fetchUserAndPost(requestId))
                        .thenReturn(Optional.of(request));


                RequestResponseDto requestResponseDto = requestService.updateRequest(requestId,updateRequestDto,userDetails).getBody();

                assertEquals(request.getId(),requestResponseDto.getRequestId());
                assertEquals(request.getUser().getNickname(),requestResponseDto.getNickname());
                assertEquals(updateRequestDto.getStatus(),requestResponseDto.getStatus());
                assertEquals(request.getUser().getProfileImgUrl(),requestResponseDto.getProfileImgUrl());

            }

        }

    }

    @Nested
    @DisplayName("포스트에 참가신청한 유저 리스트")
    class showParticipationList{
        @Nested
        @DisplayName("포스트에 참가신청한 유저 리스트 실패")
        class showParticipationListFailed{

            @Test
            @DisplayName("없는 post")
            void test11(){
                Long postId = 1L;

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                Exception exception = assertThrows(IllegalArgumentException.class,()-> requestService.showParticipationList(postId));

                assertEquals("존재하지 않는 포스트입니다.",exception.getMessage());
            }

        }

        @Nested
        @DisplayName("포스트에 참가신청한 유저 리스트 성공")
        class showParticipationListSuccess{

            @Test
            @DisplayName("유저 리스트 불러오기 성공")
            void test12(){

                Long postId = 1L;

                List<RequestResponseDto> requestResponseDtoList = new ArrayList<>();

                RequestResponseDto AcceptRequest = new RequestResponseDto(2L,secondUser.getNickname(),RequestStatus.ACCEPT,secondUser.getProfileImgUrl());

                requestResponseDtoList.add(AcceptRequest);

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(postRepository.findById(postId))
                        .thenReturn(Optional.of(post));
                when(requestRepository.showParticipationList(post,RequestStatus.MYMATCH))
                        .thenReturn(requestResponseDtoList);

                ParticipationResponseDto responseList = requestService.showParticipationList(postId).getBody();

                List<RequestResponseDto> userList = responseList.getUserList();

                RequestResponseDto responseDto = userList.get(0);

                assertEquals(1,userList.size());
                assertEquals(secondUser.getNickname(),responseDto.getNickname());
                assertEquals(RequestStatus.ACCEPT,responseDto.getStatus());
                assertEquals(2L,responseDto.getRequestId());
                assertEquals(secondUser.getProfileImgUrl(),responseDto.getProfileImgUrl());

            }
         }
    }

    @Nested
    @DisplayName("참가신청의 상태가 ACCEPT인 참가신청 리스트")
    class getAcceptRequest{

        @Nested
        @DisplayName("ACCEPT인 참가신청 리스트 실패")
        class getAcceptRequestFailed{

            @Test
            @DisplayName("없는 post")
            void test13(){
                Long postId = 1L;

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                Exception exception = assertThrows(IllegalArgumentException.class,()-> requestService.getAcceptRequest(postId));

                assertEquals("존재하지 않는 포스트입니다.",exception.getMessage());
            }

        }

        @Nested
        @DisplayName("ACCEPT인 참가신청 리스트 성공")
        class getAcceptRequestSuccess{

            @Test
            @DisplayName("ACCEPT인 참가신청 리스트 불러오기 성공")
            void test14(){

                Long postId = 1L;

                List<RequestStatus> requestStatusList =  new ArrayList<>();
                requestStatusList.add(RequestStatus.ACCEPT);
                requestStatusList.add(RequestStatus.MYMATCH);

                List<RequestResponseDto> requestResponseDtoList = new ArrayList<>();

                RequestResponseDto AcceptRequest = new RequestResponseDto(2L,secondUser.getNickname(),RequestStatus.ACCEPT,secondUser.getProfileImgUrl());

                requestResponseDtoList.add(AcceptRequest);

                RequestService requestService = new RequestService(postRepository,requestRepository,scoreRepository,
                        notificationRepository,roomRepository,userRoomRepository,template,requestRepositoryImpl,chatRepository,userRepository);
                when(postRepository.findById(postId))
                        .thenReturn(Optional.of(post));
                when(requestRepositoryImpl.getAcceptRequest(post,requestStatusList))
                        .thenReturn(requestResponseDtoList);


                List<RequestResponseDto> responseList = requestService.getAcceptRequest(postId).getBody();

                RequestResponseDto responseDto = responseList.get(0);

                assertEquals(1,responseList.size());
                assertEquals(secondUser.getNickname(),responseDto.getNickname());
                assertEquals(RequestStatus.ACCEPT,responseDto.getStatus());
                assertEquals(2L,responseDto.getRequestId());
                assertEquals(secondUser.getProfileImgUrl(),responseDto.getProfileImgUrl());

            }

        }

    }


}