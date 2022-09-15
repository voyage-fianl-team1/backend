package com.sparta.matchgi.service;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.RegisterReviewRequestDto;
import com.sparta.matchgi.dto.RegisterReviewResponseDto;
import com.sparta.matchgi.dto.ReviewListResponseDto;
import com.sparta.matchgi.dto.ShowReviewListResponseDto;
import com.sparta.matchgi.model.*;
import com.sparta.matchgi.repository.NotificationRepository;
import com.sparta.matchgi.repository.PostRepository;
import com.sparta.matchgi.repository.ReviewImgUrlRepository;
import com.sparta.matchgi.repository.ReviewRepository;
import com.sparta.matchgi.util.Image.S3Image;
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
class ReviewServiceTest {

    @Mock
    private static PostRepository postRepository;

    @Mock
    private static ReviewRepository reviewRepository;

    @Mock
    private static ReviewImgUrlRepository reviewImgUrlRepository;

    @Mock
    private static S3Image s3Image;

    @Mock
    private static SimpMessagingTemplate template;

    @Mock
    private static NotificationRepository notificationRepository;


    private static User user;

    private static User secondUser;

    private static UserDetailsImpl userDetails;

    private static UserDetailsImpl secondUserDetails;

    private static Post post;

    @BeforeAll
    static void setup(){

        user = new User(1L,"email","password","nickname",null,false);

        userDetails = new UserDetailsImpl(user);

        post = new Post(1L,user,"title", LocalDateTime.now().plusDays(1),"content", SubjectEnum.BADMINTON,0.0,0.0,0,0, MatchStatus.ONGOING,"1234",null);

        secondUser = new User(2L,"email2","password2","nickname2",null,false);

        secondUserDetails = new UserDetailsImpl(secondUser);

    }


    @Nested
    @DisplayName("댓글 쓰기")
    class registerReview{

        @Nested
        @DisplayName("댓글 쓰기 실패")
        class registerReviewFailed{

            @Test
            @DisplayName("없는 post")
            void test1(){
                Long postId = 1L;

                RegisterReviewRequestDto registerReviewRequestDto = new RegisterReviewRequestDto("review");

                ReviewService reviewService = new ReviewService(postRepository,reviewRepository,reviewImgUrlRepository,s3Image,template,notificationRepository);

                Exception exception = assertThrows(IllegalArgumentException.class,() -> reviewService.registerReview(postId,registerReviewRequestDto,userDetails));

                assertEquals("존재하지 않는 post입니다",exception.getMessage());

            }

        }

        @Nested
        @DisplayName("댓글 쓰기 성공")
        class registerReviewSuccess{

            @Test
            @DisplayName("댓글 쓰기")
            void test2(){

                Long postId = 1L;

                RegisterReviewRequestDto registerReviewRequestDto = new RegisterReviewRequestDto("review");

                ReviewService reviewService = new ReviewService(postRepository,reviewRepository,reviewImgUrlRepository,s3Image,template,notificationRepository);
                when(postRepository.findById(postId))
                        .thenReturn(Optional.of(post));


                RegisterReviewResponseDto response = reviewService.registerReview(postId,registerReviewRequestDto,secondUserDetails).getBody();

                assertEquals(registerReviewRequestDto.getContent(),response.getContent());
                assertEquals(secondUser.getNickname(),response.getNickname());


            }
        }

    }

    @Nested
    @DisplayName("댓글 리스트")
    class showReviewList{

        @Nested
        @DisplayName("댓글 리스트 실패")
        class showReviewListFailed{

            @Test
            @DisplayName("없는 post")
            void test3(){
                Long postId = 1L;

                ReviewService reviewService = new ReviewService(postRepository,reviewRepository,reviewImgUrlRepository,s3Image,template,notificationRepository);

                Exception exception = assertThrows(IllegalArgumentException.class,() -> reviewService.showReviewList(postId));

                assertEquals("존재하지 않는 post입니다",exception.getMessage());

            }

        }

        @Nested
        @DisplayName("댓글 리스트 성공")
        class showReviewListSuccess{

            @Test
            @DisplayName("댓글 리스트 불러오기")
            void test4(){
                Long postId = 1L;

                List<Review> reviewList = new ArrayList<>();
                Review review = new Review("review",post,secondUser);
                reviewList.add(review);

                ReviewService reviewService = new ReviewService(postRepository,reviewRepository,reviewImgUrlRepository,s3Image,template,notificationRepository);
                when(postRepository.findById(postId))
                        .thenReturn(Optional.of(post));
                when(reviewRepository.findByPost_fetchUserAndReviewImage(post))
                        .thenReturn(reviewList);

                ShowReviewListResponseDto response = reviewService.showReviewList(postId).getBody();

                ReviewListResponseDto responseDto = response.getReviewList().get(0);

                assertEquals(secondUser.getNickname(),responseDto.getNickname());
                assertEquals(review.getContent(),responseDto.getContent());
                assertEquals(secondUser.getProfileImgUrl(),responseDto.getProfileImgUrl());

            }

        }

    }



}