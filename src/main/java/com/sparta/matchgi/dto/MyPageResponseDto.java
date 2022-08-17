package com.sparta.matchgi.dto;

import com.sparta.matchgi.model.Score;
import com.sparta.matchgi.model.User;
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



    public MyPageResponseDto(User user,Score score){
        this.profileImgUrl=user.getProfileImgUrl();
        this.nickname=user.getNickname();
        this.win=score.getWin();
        this.lose=score.getLose();
        this.draw=score.getDraw();

    }

}
