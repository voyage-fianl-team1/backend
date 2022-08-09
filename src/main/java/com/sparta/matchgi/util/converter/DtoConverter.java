package com.sparta.matchgi.util.converter;

import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.Post;

import java.util.stream.Collectors;

public class DtoConverter {

    public static CreatePostResponseDto PostToCreateResponseDto(Post post) {

        CreatePostResponseDto createPostResponseDto = CreatePostResponseDto.builder()
                .address(post.getAddress())
                .content(post.getContent())
                .lat(post.getLat())
                .lng(post.getLng())
                .matchDeadline(post.getMatchDeadline())
                .matchStatus(post.getMatchStatus())
                .peoples(post.getPeoples())
                .peopleDeadline(post.getPeopleDeadline())
                .subject(post.getSubject())
                .title(post.getTitle())
                .images(post.getImageList().stream().map(ImgUrl::getImagePathDto).collect(Collectors.toList()))
                .build();

        return createPostResponseDto;
    }
}
