package com.sparta.matchgi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.sparta.matchgi.RedisRepository.RedisChatRepository;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.SubjectEnum;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.repository.*;
import com.sparta.matchgi.util.Image.S3Image;
import com.sparta.matchgi.util.converter.DateConverter;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.LocalGregorianCalendar;
import java.util.Date;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.repository.util.ClassUtils.ifPresent;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private static PostRepository postRepository;
    @Mock
    private static ImageRepository imageRepository;
    @Mock
    AmazonS3 amazonS3;
    @Mock
    PostRepositoryImpl postRepositoryImpl;
    @Mock
    RoomRepository roomRepository;
    @Mock
    UserRoomRepository userRoomRepository;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    RedisChatRepository redisChatRepository;
    @Mock
    ChatRepository chatRepository;
    @Mock
    RequestRepository requestRepository;
    @Mock
    S3Image s3Image;
    @Mock
    NotificationRepository notificationRepository;

    private static User user1;

    private static User user2;

    private static UserDetailsImpl userDetailsImpl1;
    private static UserDetailsImpl userDetailsImpl2;


    private static Post post;

    @BeforeAll
    static void createUser(){
        user1 = new User(1L,"email","password","nickname",null,false);
        userDetailsImpl1=new UserDetailsImpl(user1);

        user2=new User(2L,"email2","password2","nickname2",null,false);
        userDetailsImpl2=new UserDetailsImpl(user2);

    }

    @Test
    @DisplayName("포스트 생성")
    void create_Post() {
        Long postId=1L;

        Date now = new Date();
        CreatePostRequestDto postRequestDto=new CreatePostRequestDto("TITLE",now,14.9,23.5,"대구광역시 북구",SubjectEnum.SOCCER,"축구하실분구함");
        Post post=new Post(postRequestDto,userDetailsImpl1);

        PostService postService = new PostService(postRepository,imageRepository,amazonS3,postRepositoryImpl,roomRepository,userRoomRepository,reviewRepository,redisChatRepository,chatRepository,requestRepository,s3Image,notificationRepository);
        lenient().when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));


        CreatePostResponseDto postResponseDto=postService.createPost(postRequestDto,userDetailsImpl1).getBody();

        assertEquals(postRequestDto.getTitle(),postResponseDto.getTitle());

    }



    @Test
    @DisplayName("포스트 읽기")
    void getPost() {

    }
//
//    @Test
//    void getGuestPost() {
//    }
//
//    @Test
//    void imageDelete() {
//    }
//
//    @Test
//    void getImgUrl() {
//    }
//
//    @Test
//    void imageUpload() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void editPost() {
//    }
//
//    @Test
//    void deletePost() {
//    }
//
//    @Test
//    void filterDtoSlice() {
//    }
//
//    @Test
//    void searchDtoSlice() {
//    }
//
//    @Test
//    void changeStatus() {
//    }
//
//    @Test
//    void findLocation() {
//    }
//
//    @Test
//    void confirmAuthority() {
//    }
}