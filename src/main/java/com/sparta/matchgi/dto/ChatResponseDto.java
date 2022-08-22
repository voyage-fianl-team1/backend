package com.sparta.matchgi.dto;

import com.sparta.matchgi.model.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ChatResponseDto {

    private Long id;

    private String nickname;

    private String profileImgUrl;

    private String message;

    private LocalDateTime createdAt;


    public ChatResponseDto(Chat chat){
        this.nickname = chat.getUser().getNickname();
        this.profileImgUrl = chat.getUser().getProfileImgUrl();
        this.message = chat.getMessage();
        this.createdAt = chat.getCreatedAt();
    }



}


