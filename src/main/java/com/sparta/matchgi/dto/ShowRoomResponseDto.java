package com.sparta.matchgi.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ShowRoomResponseDto {

    private Long roomId;

    private Long postId;

    private String title;

    private String imgUrl;

    private Long chatId;

    private String message;

    private String nickname;

    private LocalDateTime createdAt;

    private Long unreadMessageCount;

}
