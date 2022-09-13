package com.sparta.matchgi.controller;


import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.RegisterReviewRequestDto;
import com.sparta.matchgi.dto.RegisterReviewResponseDto;
import com.sparta.matchgi.dto.ShowReviewListResponseDto;
import com.sparta.matchgi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/api/reviews/{postId}")
    public ResponseEntity<RegisterReviewResponseDto> registerReview(@PathVariable Long postId, @RequestBody RegisterReviewRequestDto registerReviewRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return reviewService.registerReview(postId,registerReviewRequestDto,userDetails);
    }

    @PostMapping("/api/images/reviews/{reviewId}")
    public ResponseEntity<?> uploadReviewImage(@PathVariable Long reviewId, @RequestPart List<MultipartFile> files){
        return reviewService.uploadReviewImage(reviewId,files);
    }

    @GetMapping("/api/reviews/{postId}")
    public ResponseEntity<ShowReviewListResponseDto> showReviewList(@PathVariable Long postId){
        return reviewService.showReviewList(postId);

    }
}
