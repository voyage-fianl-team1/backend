package com.sparta.matchgi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Getter
public class PostFilterDto {

    private Long postId;
    private String title;
    private SubjectEnum subject;
    private int viewCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime peopleDeadline;
    private int requestCount;

    private LocalDateTime createdAt;

//    public PostFilterDto(Post post){
//        this.title=post.getTitle();
//        this.postId=post.getId();
//        this.peopleDeadline=post.getPeopleDeadline();
//        this.subject=post.getSubject();
//        this.viewCount=post.getViewCount();
//        this.requestCount=post.getRequestCount();
//        this.createdAt=post.getCreatedAt();
//    }

}
