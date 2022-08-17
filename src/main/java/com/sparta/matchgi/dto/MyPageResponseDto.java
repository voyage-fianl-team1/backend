package com.sparta.matchgi.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MyPageResponseDto {
    private String profileImgUrl;
    private String nickname;
    private int win;
    private int lose;
    private int draw;


}
