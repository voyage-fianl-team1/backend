package com.sparta.matchgi.dto;

import com.sparta.matchgi.model.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRankingResponseDto {

    private String nickname;

    private SubjectEnum subject;

    private String profileUrl;

    private int win;
}
