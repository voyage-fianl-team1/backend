package com.sparta.matchgi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.sparta.matchgi.RedisRepository.RedisChatRepository;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.*;
import com.sparta.matchgi.model.*;
import com.sparta.matchgi.repository.*;
import com.sparta.matchgi.util.Image.S3Image;
import com.sparta.matchgi.util.converter.DateConverter;
import com.sparta.matchgi.util.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import javax.persistence.Query;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3 amazonS3;
    private final PostRepositoryImpl postRepositoryImpl;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;
    private final ReviewRepository reviewRepository;
    private final RedisChatRepository redisChatRepository;
    private final ChatRepository chatRepository;
    private final RequestRepository requestRepository;
    private final S3Image s3Image;
    private final EntityManager em;
    private final double distanceKm = 5000.0;



    @Value("${S3.bucket.name}")
    private String bucket;

    //포스트 생성하기(완료)
    public ResponseEntity<?> createPost(
            CreatePostRequestDto createPostRequestDto, UserDetailsImpl userDetails)
            throws IOException
    {
        Post post = new Post(createPostRequestDto, userDetails);
        postRepository.save(post);

        Room room = new Room(post.getId(),userDetails.getUser(),post);
        roomRepository.save(room);

        UserRoom userRoom = new UserRoom(userDetails.getUser(),room, DateConverter.millsToLocalDateTime(System.currentTimeMillis()));
        userRoomRepository.save(userRoom);
        CreatePostResponseDto createPostResponseDto = DtoConverter.PostToCreateResponseDto(post,1);

        return new ResponseEntity<>(createPostResponseDto, HttpStatus.valueOf(201));
    }


    //상세 게시글 보기(완료)
    public ResponseEntity<?> getPost(Long postId,UserDetailsImpl userDetails){
        Post post=postRepository.findById(postId)
                .orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        int owner=-1;
        if(userDetails.getUser().getEmail().equals(post.getUser().getEmail())){
            owner=1;
        }

        CreatePostResponseDto createPostResponseDto = DtoConverter.PostToCreateResponseDto(post,owner);

        return new ResponseEntity<>(createPostResponseDto, HttpStatus.valueOf(201));

    }

    //이미지 db에서 지우기기
    public void imageDelete(String objectKey,UserDetailsImpl userDetails){
        ImgUrl imageUrl=imageRepository.findImgUrlByPath(objectKey); //post 마다 ImgUrl(path,url)
        imageRepository.delete(imageUrl);
    }

    //이미지 url 받아오기(완료)
    public ImageUrlDto getImgUrl(String path){
        ImageUrlDto imageUrlDto=new ImageUrlDto(amazonS3.getUrl(bucket,path).toString());

        return imageUrlDto;
    }


    //이미지 업로드하기(완료)
    public void imageUpload(Long postId, List<MultipartFile> files,UserDetailsImpl userDetails) throws IOException{
        List<ImagePathDto> imagePathDtoList=new ArrayList<>();

        for(MultipartFile file:files){
            ImagePathDto imagePathDto=update(file); //파일을 하나씩 s3에 업데이트
            imagePathDtoList.add(imagePathDto); //파일 path를 하나씩 db에 업데이트
        }

        Post post=postRepository.findPostById(postId); //어떤 포스트에 추가할지, 포스트 불러오기

        List<ImgUrl> imglist=new ArrayList<>();
        for (ImagePathDto imagePathdto : imagePathDtoList) {
            ImageUrlDto imgurl=getImgUrl(imagePathdto.getPath());//path에 해당하는 url 받기
            ImgUrl imgUrl = new ImgUrl(post, imagePathdto.getPath(),imgurl.getUrl());
            imglist.add(imgUrl);
            //post.addImgUrl(imgUrl); //imgurl 따로 post에 저장
        }
        imageRepository.saveAll(imglist);
    }

    //이미지 버킷에 업로드하기(완료)
    public ImagePathDto update(MultipartFile file) throws IOException {
        String filename = s3Image.upload(file);
        ImagePathDto imagePathDto = new ImagePathDto(filename);
        return imagePathDto;
    }

    //포스트 수정하기(완료)
    public ResponseEntity<?> editPost(
            Long postId,CreatePostRequestDto createPostRequestDto,UserDetailsImpl userDetails)
            throws IOException {

        Post post=postRepository.findPostById(postId);
        if(!userDetails.getUser().getEmail().equals(post.getUser().getEmail()))
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        int owner=-1;
        if(userDetails.getUser().getEmail().equals(post.getUser().getEmail())){
            owner=1;
        }
        post.updatePost(createPostRequestDto,userDetails);
        postRepository.save(post);

        CreatePostResponseDto createPostResponseDto = DtoConverter.PostToCreateResponseDto(post,owner);

        return new ResponseEntity<>(createPostResponseDto, HttpStatus.valueOf(201));
    }


    //포스트 지우기(완료)
    //관련된 거 다 삭제하기
    public ResponseEntity<?> deletePost(Long postId, UserDetailsImpl userDetails){
        Post post=postRepository.findById(postId)
                .orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if(!userDetails.getUser().getEmail().equals(post.getUser().getEmail())){
            throw new IllegalArgumentException("접근 권한이 없는 사용자입니다.");
        }
        Room room=roomRepository.findByPostId(postId);

        //post->room->userRoom,Chat
        Long roomId=room.getId();

        UserRoom userRoom=userRoomRepository.findByRoom(room);
        Long userRoomId=userRoom.getId();
        userRoomRepository.deleteById(userRoomId);

        //Post-ImgUrl,Request,Review,Room
        //Room-chat,UserRoom

        //reviewRepository.de
        //
        //
        // List<RedisChat> redislist=redisChatRepository.findByRoomIdOrderByCreatedAt(roomId);

        chatRepository.deleteAllByRoom(room);
        userRoomRepository.deleteAllByRoom(room);

        roomRepository.deleteById(roomId);
        reviewRepository.deleteAllByPost(post);
        requestRepository.deleteAllByPost(post);
        imageRepository.deleteAllByPost(post);
        postRepository.deleteById(postId);

        return new ResponseEntity<>(HttpStatus.valueOf(201));
    }


    public Slice<PostFilterDto> filterDtoSlice(String subject,String sort,int size,int page){

        Pageable pageable= PageRequest.of(page,size);

        return postRepositoryImpl.findAllBySubjectOrderByCreatedAt(subject,sort,pageable);
    }

    public Slice<PostFilterDto> searchDtoSlice(String search, int page, int size){
        System.out.println(search);
        Pageable pageable= PageRequest.of(page,size);

        return postRepositoryImpl.findAllBySearchOrderByCreatedAt(search,pageable);
    }

    public ResponseEntity<?> changeStatus(Long postId,UserDetailsImpl userDetails){

        Post post=postRepository.findPostById(postId);
        if(!userDetails.getUser().getEmail().equals(post.getUser().getEmail())){
            throw new IllegalArgumentException("접근 권한이 없는 사용자입니다.");
        }

        if(reviewRepository.findByPost(post).size()>0){
            throw new IllegalArgumentException("리뷰가 존재하여 경기상태를 바꿀수 없습니다.");
        }
        if(post.getMatchStatus().equals(MatchStatus.ONGOING)){
            post.changeStatus(MatchStatus.MATCHEND);
        }
        else
            post.changeStatus(MatchStatus.ONGOING);

        return new ResponseEntity<>("정상적으로 경기상태가 변경되었습니다",HttpStatus.valueOf(200));
    }


    //거리찾기-querydsl 사용
    public List<PostFilterDto> findLocation(double lat,double lng){
        return postRepositoryImpl.findAllByLocation(lat,lng);
    }

    //거리찾기-nativeQuery 사용
    public List<PostFilterDto> findLocationWithQuery(double lat, double lng) throws ParseException {return queryLocation(lat,lng);}
    @Transactional
    public List<PostFilterDto> queryLocation(double lat,double lng)throws ParseException {
        //List<PostFilterDto> posts = new ArrayList<>();
        System.out.println("쿼리문 진입");
        Query query = (Query) em.createNativeQuery("SELECT id, created_at, title, lat, lng,viewCount,matchDeadline,requestCount,matchStatus,subject,"
                        + "ROUND(ST_DISTANCE_SPHERE(:myPoint, POINT(p.lng, p.lat))) AS 'distance' "//두 좌표 사이의 거리
                        + "FROM post AS p "
                        + "WHERE ST_DISTANCE_SPHERE(:myPoint, POINT(p.lng, p.lat)) < 5000"//5km 이내
                        + "ORDER BY p.id ", Post.class)//현위치~경기 위치까지의 거리 순으로 정렬
                .setParameter("myPoint", makePoint(lng, lat));
        //.setParameter("distance", 5000);
        List<PostFilterDto> posts = query.getResultList();
        //Query도 적절한 타입으로 import 잘해줄 것
        //import org.springframework.data.jpa.repository.Query; 이걸로 하면 안됨
        return posts;
    }

    public Point makePoint(double lng, double lat) throws org.locationtech.jts.io.ParseException {
        String pointWKT = String.format("POINT(%s %s)", lng, lat);//Poing는 경도,위도 순 입력
        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
        return (Point) new WKTReader().read(pointWKT);
    }


}