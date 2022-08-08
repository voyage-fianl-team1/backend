package com.sparta.matchgi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ImgUrl {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "IMAGE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Column(nullable = false)
    private String imgUrl;
}
