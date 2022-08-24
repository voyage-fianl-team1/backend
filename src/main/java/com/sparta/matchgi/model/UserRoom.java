package com.sparta.matchgi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoom {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USER_ROOM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @Column(nullable = false)
    private LocalDateTime lastActive;

    public UserRoom(User user,Room room,LocalDateTime lastActive){
        this.user = user;
        this.room = room;
        this.lastActive = lastActive;
    }

    public void updateLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }
}
