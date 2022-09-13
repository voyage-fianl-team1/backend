package com.sparta.matchgi.service;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.NotificationDetailResponseDto;
import com.sparta.matchgi.model.*;
import com.sparta.matchgi.repository.NotificationRepository;
import com.sparta.matchgi.repository.NotificationRepositoryImpl;
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
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationRepositoryImpl notificationRepositoryImpl;

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
    @DisplayName("알람 리스트")
    class getNotice{

        @Nested
        @DisplayName("알람 리스트 성공")
        class getNoticeSuccess{

            @Test
            @DisplayName("알람 리스트 불러오기")
            void test1(){

                List<NotificationDetailResponseDto> detailResponseDtoList = new ArrayList<>();
                NotificationDetailResponseDto detailResponseDto = new NotificationDetailResponseDto(1L,1L,"notification",LocalDateTime.now(),false);
                detailResponseDtoList.add(detailResponseDto);

                NotificationService notificationService = new NotificationService(notificationRepository,notificationRepositoryImpl);
                when(notificationRepositoryImpl.getNotice(user))
                        .thenReturn(detailResponseDtoList);

                List<NotificationDetailResponseDto> response = notificationService.getNotice(userDetails).getBody();
                NotificationDetailResponseDto responseDto = response.get(0);

                assertEquals(1L,response.size());
                assertEquals(detailResponseDto.getPostId(),responseDto.getPostId());
                assertEquals(detailResponseDto.getId(),responseDto.getId());
                assertEquals(detailResponseDto.getContent(),responseDto.getContent());
                assertEquals(detailResponseDto.getCreatedAt(),responseDto.getCreatedAt());
                assertEquals(detailResponseDto.isIsread(),responseDto.isIsread());

            }
        }
    }

    @Nested
    @DisplayName("알람 읽기 처리")
    class readProcessing{

        @Nested
        @DisplayName("알람 읽기 처리 실패")
        class readProcessingFailed{

            @Test
            @DisplayName("없는 알림")
            void test2(){

                Long notificationId = 1L;

                NotificationService notificationService = new NotificationService(notificationRepository,notificationRepositoryImpl);

                Exception exception = assertThrows(IllegalArgumentException.class,()-> notificationService.readProcessing(notificationId));

                assertEquals("없는 알림입니다.",exception.getMessage());

            }

        }

        @Nested
        @DisplayName("알람 읽기 처리 성공")
        class readProcessingSuccess{

            @Test
            @DisplayName("알람 읽기 처리")
            void test3(){
                Long notificationId = 1L;

                Notification notification = new Notification("notification",user,post);

                NotificationService notificationService = new NotificationService(notificationRepository,notificationRepositoryImpl);
                when(notificationRepository.findById(notificationId))
                        .thenReturn(Optional.of(notification));

                ResponseEntity<?> response =  notificationService.readProcessing(notificationId);

                assertEquals(HttpStatus.OK,response.getStatusCode());

            }
        }
    }


}