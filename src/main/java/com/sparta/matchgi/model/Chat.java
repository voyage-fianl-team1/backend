package com.sparta.matchgi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chat{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CHAT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private Room room;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Chat(Room room, String message, User user,LocalDateTime createdAt) {
        this.room = room;
        this.message = message;
        this.user = user;
        this.createdAt = createdAt;
    }
}
