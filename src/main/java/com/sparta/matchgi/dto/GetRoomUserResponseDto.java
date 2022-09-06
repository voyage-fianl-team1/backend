package com.sparta.matchgi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GetRoomUserResponseDto {

    private Long userId;

    private String profileImgUrl;

    private String nickname;

}
