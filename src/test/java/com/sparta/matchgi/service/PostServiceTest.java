package com.sparta.matchgi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.sparta.matchgi.RedisRepository.RedisChatRepository;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.dto.ImageUrlDto;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.SubjectEnum;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.repository.*;
import com.sparta.matchgi.util.Image.S3Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

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
        post=new Post(postRequestDto,userDetailsImpl1);

        PostService postService = new PostService(postRepository,imageRepository,amazonS3,postRepositoryImpl,roomRepository,userRoomRepository,reviewRepository,redisChatRepository,chatRepository,requestRepository,s3Image,notificationRepository);
        lenient().when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));


        CreatePostResponseDto postResponseDto=postService.createPost(postRequestDto,userDetailsImpl1).getBody();

        assertEquals(postRequestDto.getTitle(),postResponseDto.getTitle());

    }



    @Test
    @DisplayName("포스트 읽기")
    void getPost() {

        Long postId = 1L;
        PostService postService = new PostService(postRepository,imageRepository,amazonS3,postRepositoryImpl,roomRepository,userRoomRepository,reviewRepository,redisChatRepository,chatRepository,requestRepository,s3Image,notificationRepository);
        lenient().when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));
        CreatePostResponseDto postResponseDto=postService.getPost(postId,userDetailsImpl1).getBody();

        assertEquals(post.getTitle(),postResponseDto.getTitle());
        assertEquals(post.getSubject(),postResponseDto.getSubjectValue());
        assertEquals(post.getId(),postResponseDto.getPostId());
        assertEquals(post.getMatchDeadline(),postResponseDto.getMatchDeadline());
        assertEquals(post.getLat(),postResponseDto.getLat());
        assertEquals(post.getLng(),postResponseDto.getLng());
        assertEquals(post.getAddress(),postResponseDto.getAddress());
        assertEquals(post.getContent(),postResponseDto.getContent());
        assertEquals(post.getMatchStatus(),postResponseDto.getMatchStatus());
        assertEquals(post.getViewCount(),postResponseDto.getViewCount());




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
    @Test
    void getImgUrl() {
        String path="s3://matchgi-bucket/1e8511ea-6924-46ae-9bd9-e50f152fc637_프사.jpg";
        PostService postService = new PostService(postRepository,imageRepository,amazonS3,postRepositoryImpl,roomRepository,userRoomRepository,reviewRepository,redisChatRepository,chatRepository,requestRepository,s3Image,notificationRepository);
        when(imageRepository.findImgUrlByPath(path))
                .thenReturn(Optional.of(path));

        ImageUrlDto imageUrlDto=new ImageUrlDto(path);
        ImageUrlDto imageUrl=postService.getImgUrl(path);

        assertEquals(imageUrlDto,imageUrl);

    }
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