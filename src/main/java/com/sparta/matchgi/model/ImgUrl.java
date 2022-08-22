package com.sparta.matchgi.model;

import com.sparta.matchgi.dto.ImagePathDto;
import com.sparta.matchgi.dto.ImageUrlDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.net.URL;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor
public class ImgUrl{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IMAGE_ID")//포스트별로 따로 증가하지 않음
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(nullable = false)
    private String path;//이미지 업로드-추가

    @Column(nullable = false)
    private String url;//이미지 업로드-추가

    public ImgUrl(Post post,String path,String url){
        this.post =post;
        this.path = path;
        this.url=url;
    }


    public ImagePathDto getImagePathDto(){
        return new ImagePathDto(this.path);
    }

    public ImageUrlDto getImageUrlDto(){return new ImageUrlDto(this.url);}


}
