package com.sparta.matchgi.dto;

import com.sparta.matchgi.model.MatchStatus;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostResponseDto {
    private String title;
    private LocalDate peopleDeadline;
    private LocalDate matchDeadline;
    private int peoples;
    private double lat;
    private double lng;
    private String address;
    private SubjectEnum subject;
    private String content;
    private MatchStatus matchStatus;

}
