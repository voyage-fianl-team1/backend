package com.sparta.matchgi.dto;

import com.sparta.matchgi.model.RequestStatus;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetScoresResponseDto {

    private LocalDateTime matchDeadline;

    private SubjectEnum subject;

    private RequestStatus status;

    private String imgUrl;

    private String title;

    private Long postId;


}
