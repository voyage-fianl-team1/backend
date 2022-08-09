package com.sparta.matchgi.dto;

import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PostRequestDto {
    private String title;
    private LocalDate peopleDeadline;
    private LocalDate matchDeadline;
    private int peoples;
    private double lat;
    private double lng;
    private String address;
    private SubjectEnum subject;
    private String content;
    private List<ImgUrl> imageList;

}
