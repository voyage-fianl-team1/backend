package com.sparta.matchgi.model;


import com.sparta.matchgi.dto.ReviseUserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USER_ID")
    private Long id;


    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;


    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;

    }

    public void updateNick(ReviseUserRequestDto reviseUserRequestDto){
        this.nickname = reviseUserRequestDto.getNickname();
    }

    public void changePassword(String password){
        this.password = password;
    }
}