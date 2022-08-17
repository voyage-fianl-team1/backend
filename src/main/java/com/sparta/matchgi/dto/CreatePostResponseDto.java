package com.sparta.matchgi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.matchgi.model.MatchStatus;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreatePostResponseDto {
    private String title;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime peopleDeadline;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime matchDeadline;

    private double lat;

    private double lng;

    private String address;

    private SubjectEnum subject;

    private String content;

    private MatchStatus matchStatus;

    private List<ImagePathDto> images;

    private int owner;

    public CreatePostResponseDto(Post post, List<ImagePathDto> imagePathDtos){
        this.title=post.getTitle();
//        this.peopleDeadline=post.getPeopleDeadline();
//        this.matchDeadline=post.getMatchDeadline();
        this.address= post.getAddress();
        this.subject=post.getSubject();
        this.content=post.getContent();
        this.matchStatus=post.getMatchStatus();
        this.images=imagePathDtos;
        this.owner=post.getOwner();
    }


}
