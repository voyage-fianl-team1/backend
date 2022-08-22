package com.sparta.matchgi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostListDto {


    private Long postId;
    private String title;
    private SubjectEnum subject;
    private  int viewCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime peopleDeadline;

    private int requestCount;







}
