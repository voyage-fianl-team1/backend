package com.sparta.matchgi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.MatchStatus;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreatePostResponseDto {
    private String title;

    private Long postId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime matchDeadline;

    private double lat;

    private double lng;

    private String address;

    private String subject;

    private String content;

    private MatchStatus matchStatus;

    private List<ImagePathDto> imgpaths;

    private List<ImageUrlDto> imgurls;

    private List<ImageUrlDto> imgurl;

    private int owner;

    private int viewCount;

    private int player;

    private String nickname;

    private String profileImgUrl;

    private SubjectEnum subjectValue;

    public CreatePostResponseDto (Post post,int owner, int player) {

        this.postId = post.getId();
        this.address = post.getAddress();
        this.content = post.getContent();
        this.lat = post.getLat();
        this.lng = post.getLng();
        this.matchDeadline = post.getMatchDeadline();
        this.matchStatus = post.getMatchStatus();
        this.subject = post.getSubject().getValue();
        this.title = post.getTitle();
        this.imgurls = post.getImageList().stream().map(ImgUrl::getImageUrlDto).collect(Collectors.toList());
        this.imgpaths = post.getImageList().stream().map(ImgUrl::getImagePathDto).collect(Collectors.toList());
        this.imgurl = post.getImageList().stream().map(ImgUrl::getImageUrlDto).collect(Collectors.toList());
        this.viewCount = post.getViewCount();
        this.profileImgUrl = post.getUser().getProfileImgUrl();
        this.nickname = post.getUser().getNickname();
        this.subjectValue = post.getSubject();
        this.owner = owner;
        this.player = player;

    }



}