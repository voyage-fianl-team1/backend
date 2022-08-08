package com.sparta.matchgi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Request {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "REQUEST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RequestStatusEnum requestStatus;
}
