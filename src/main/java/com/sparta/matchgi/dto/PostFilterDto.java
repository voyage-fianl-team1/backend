package com.sparta.matchgi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.matchgi.model.MatchStatus;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostFilterDto {


    private Long postId;
    private String title;
    private SubjectEnum subject;
    private int viewCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime matchDeadline;
    private int requestCount;

    private LocalDateTime createdAt;

    private MatchStatus matchStatus;

    public PostFilterDto(Post post){
        this.title=post.getTitle();
        this.postId=post.getId();
        this.matchDeadline=post.getMatchDeadline();
        this.subject=post.getSubject();
        this.viewCount=post.getViewCount();
        this.requestCount=post.getRequestCount();
        this.createdAt=post.getCreatedAt();
        this.matchStatus=post.getMatchStatus();
    }

}
