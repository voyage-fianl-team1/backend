package com.sparta.matchgi.service;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.*;
import com.sparta.matchgi.model.*;
import com.sparta.matchgi.repository.NotificationRepository;
import com.sparta.matchgi.repository.PostRepository;
import com.sparta.matchgi.repository.ReviewImgUrlRepository;
import com.sparta.matchgi.repository.ReviewRepository;
import com.sparta.matchgi.util.Image.S3Image;
import com.sparta.matchgi.util.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final PostRepository postRepository;

    private final ReviewRepository reviewRepository;

    private final ReviewImgUrlRepository reviewImgUrlRepository;

    private final S3Image s3Image;

    private final SimpMessagingTemplate template;

    private final NotificationRepository notificationRepository;

    @Value("${S3.Url}")
    private String S3Url;

    public ResponseEntity<RegisterReviewResponseDto> registerReview(Long postId, RegisterReviewRequestDto registerReviewRequestDto, UserDetailsImpl userDetails) {

        Optional<Post> postFound = postRepository.findById(postId);
        if(!postFound.isPresent()){
            throw new IllegalArgumentException("존재하지 않는 post입니다");
        }

        Post post = postFound.get();

        User user = userDetails.getUser();

        Review review = new Review(registerReviewRequestDto.getContent(),post,user);

        reviewRepository.save(review);

        if(!user.getId().equals(post.getUser().getId())){
            Notification notification = new Notification(post.getTitle()+"에 "+user.getNickname()+"님이 댓글을 작성했습니다.",post.getUser(),post);
            notificationRepository.save(notification);
            NotificationDetailResponseDto notificationDetailResponseDto = new NotificationDetailResponseDto(notification);
            template.convertAndSend("/room/user/"+post.getUser().getId(),notificationDetailResponseDto);
        }

        return new ResponseEntity<>(new RegisterReviewResponseDto(review.getId(),review.getContent(),review.getUser().getNickname()), HttpStatus.valueOf(201));

    }

    public ResponseEntity<?> uploadReviewImage(Long reviewId, List<MultipartFile> files) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 review 입니다")
        );

        try{
            for(MultipartFile file:files){
                String filePath = s3Image.upload(file);
                ReviewImgUrl reviewImgUrl = new ReviewImgUrl(S3Url+filePath,review);
                reviewImgUrlRepository.save(reviewImgUrl);
            }
        }catch (IOException e){
            reviewRepository.delete(review);
            return new ResponseEntity<>("파일업로드에 실패하였습니다.",HttpStatus.valueOf(500));
        }
        return new ResponseEntity<>("성공적으로 파일이 업로드되었습니다",HttpStatus.valueOf(201));

    }

    public ResponseEntity<ShowReviewListResponseDto> showReviewList(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 post입니다")
        );

        List<Review> reviewList = reviewRepository.findByPost(post);

        List<ReviewListResponseDto> reviewListResponseDtoList = DtoConverter.reviewListToReviewListResponseDto(reviewList);

        return new ResponseEntity<>(new ShowReviewListResponseDto(reviewListResponseDtoList),HttpStatus.valueOf(200));

    }
}
