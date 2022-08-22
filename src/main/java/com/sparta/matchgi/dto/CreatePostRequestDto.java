package com.sparta.matchgi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreatePostRequestDto {

    private String title;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date matchDeadline;

    private double lat;

    private double lng;

    private String address;

    private SubjectEnum subject;

    private String content;



}
