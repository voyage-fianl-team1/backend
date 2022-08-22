package com.sparta.matchgi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "REVIEW_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int star;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(cascade =CascadeType.ALL,mappedBy = "review")
    private List<ReviewImgUrl> reviewImageList = new ArrayList<>();

    public Review(String title, String content, int star, Post post, User user) {
        this.title = title;
        this.content = content;
        this.star = star;
        this.post = post;
        this.user = user;
    }
}
