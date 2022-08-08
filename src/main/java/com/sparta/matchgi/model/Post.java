package com.sparta.matchgi.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate peopleDeadline;

    @Column(nullable = false)
    private LocalDate matchDeadline;

    @Column(nullable = false)
    private int peoples; //전체 모집 인원


    @Column(nullable = false)
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private SubjectEnum subject;

    @Column(nullable = false)
    private Double Lat;

    @Column(nullable = false)
    private Double Lng;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int requestCount; //경기목록더보기-추가


    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus matchStatus;


    @Column(nullable = false)
    private String address;

    @OneToMany(cascade =CascadeType.ALL,mappedBy ="post")
    private List<ImgUrl> imageList=new ArrayList<>();





}
