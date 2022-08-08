package com.sparta.matchgi.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Message extends Timestamped{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MESSAGE_ID")
    private Long id;


    @Column(nullable = false)
    private String content;

    //타겟룸은 어떻게?

}
