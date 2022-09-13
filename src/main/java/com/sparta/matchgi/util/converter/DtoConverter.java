package com.sparta.matchgi.util.converter;

import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.dto.ReviewListResponseDto;
import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.Review;
import com.sparta.matchgi.model.ReviewImgUrl;

import java.util.List;
import java.util.stream.Collectors;

public class DtoConverter {

    public static CreatePostResponseDto PostToCreateResponseDto(Post post,int owner, int player) {

        CreatePostResponseDto createPostResponseDto = CreatePostResponseDto.builder()
                .postId(post.getId())
                .address(post.getAddress())
                .content(post.getContent())
                .lat(post.getLat())
                .lng(post.getLng())
                .matchDeadline(post.getMatchDeadline())
                .matchStatus(post.getMatchStatus())
                .subject(post.getSubject().getValue())
                .title(post.getTitle())
                .imgurls(post.getImageList().stream().map(ImgUrl::getImageUrlDto).collect(Collectors.toList()))
                .imgpaths(post.getImageList().stream().map(ImgUrl::getImagePathDto).collect(Collectors.toList()))
                .imgurl(post.getImageList().stream().map(ImgUrl::getImageUrlDto).collect(Collectors.toList()))
                .owner(owner)
                .player(player)
                .viewCount(post.getViewCount())
                .profileImgUrl(post.getUser().getProfileImgUrl())
                .nickname(post.getUser().getNickname())
                .subjectValue(post.getSubject())
                .build();

        return createPostResponseDto;
    }


    public static List<ReviewListResponseDto> reviewListToReviewListResponseDto(List<Review> reviewList){
        List<ReviewListResponseDto> reviewListResponseDtoList = reviewList.stream().map(r->
                ReviewListResponseDto.builder()
                        .reviewId(r.getId())
                        .imgUrlList(r.getReviewImageList().stream().map(ReviewImgUrl::getUrl).collect(Collectors.toList()))
                        .nickname(r.getUser().getNickname())
                        .content(r.getContent())
                        .profileImgUrl(r.getUser().getProfileImgUrl())
                        .createdAt(r.getCreatedAt())
                        .build()
        ).collect(Collectors.toList());

        return reviewListResponseDtoList;
    }

}
