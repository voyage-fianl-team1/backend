package com.sparta.matchgi.util.converter;

import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.dto.ParticipationResponseDto;
import com.sparta.matchgi.dto.RequestResponseDto;
import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.Request;

import java.util.List;
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

    public static List<RequestResponseDto> RequestToRequestResponseDto(List<Request> requestList) {

        List<RequestResponseDto> requestResponseDtoList = requestList.stream().map(r->
                RequestResponseDto.builder()
                        .nickname(r.getUser().getNickname())
                        .status(r.getRequestStatus())
                        .build()
                ).collect(Collectors.toList());

        return requestResponseDtoList;
    }
}
