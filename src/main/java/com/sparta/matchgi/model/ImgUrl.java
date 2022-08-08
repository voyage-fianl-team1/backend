package com.sparta.matchgi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ImgUrl extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IMAGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(nullable = false)
    private String objectKey; //이미지 업로드-추가

    @Column(nullable = false)
    private String path;//이미지 업로드-추가

}
