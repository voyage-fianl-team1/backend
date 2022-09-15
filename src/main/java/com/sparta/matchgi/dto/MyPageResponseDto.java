package com.sparta.matchgi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MyPageResponseDto {

    private Long id;

    private String profileImgUrl;

    private String nickname;

    private Object win;

    private Object draw;

    private Object lose;

}
