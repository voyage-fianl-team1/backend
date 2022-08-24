package com.sparta.matchgi.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MyPageResponseDto {

    private Long id;

    private String profileImgUrl;

    private String nickname;

    private Long win;

    private Long lose;

    private Long draw;

}
