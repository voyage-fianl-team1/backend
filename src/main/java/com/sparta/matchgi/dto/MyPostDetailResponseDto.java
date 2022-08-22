package com.sparta.matchgi.dto;

import com.sparta.matchgi.model.MatchStatus;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MyPostDetailResponseDto {
    private Long id;
    private String title;
    private SubjectEnum subject;

    public MyPostDetailResponseDto(Post post){
        this.id=post.getId();
        this.title=post.getTitle();
        this.subject=post.getSubject();
    }


}
