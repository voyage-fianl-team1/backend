package com.sparta.matchgi.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Score {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SCORE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private SubjectEnum subject;

    @Column(nullable = false)
    private int win;

    @Column(nullable = false)
    private int lose;

    @Column(nullable = false)
    private int draw;
}
