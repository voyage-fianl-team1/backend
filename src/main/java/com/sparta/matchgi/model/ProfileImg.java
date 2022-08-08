package com.sparta.matchgi.model;

import javax.persistence.*;

public class ProfileImg {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PROFILEIMG_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String profileImgUrl;
}
