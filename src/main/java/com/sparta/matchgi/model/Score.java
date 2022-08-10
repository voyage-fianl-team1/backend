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
    @Enumerated(value = EnumType.STRING)
    private SubjectEnum subject;

    @Column(nullable = false)
    private int win;

    @Column(nullable = false)
    private int lose;

    @Column(nullable = false)
    private int draw;

    public void addWin(){
        this.win += 1;
    }
    public void addLose(){
        this.lose += 1;
    }
    public void addDraw(){
        this.draw += 1;
    }

    public Score(User user,SubjectEnum subject){
        this.user = user;
        this.subject = subject;
    }
}
