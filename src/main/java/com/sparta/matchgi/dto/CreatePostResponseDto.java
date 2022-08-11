package com.sparta.matchgi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.matchgi.model.MatchStatus;
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

    private int peoples;

    private double lat;

    private double lng;

    private String address;

    private SubjectEnum subject;

    private String content;

    private MatchStatus matchStatus;

    private List<ImagePathDto> images;

}