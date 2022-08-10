package com.sparta.matchgi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum RequestStatus {
    PENDING{
        @Override
        public String getNotificationContent(String title,String nickname) {
            return nickname+"님이 "+title+"에 참가신청하셨습니다";
        }

        @Override
        public void addScore(Score score) {
        }
    },
    ACCEPT{
        @Override
        public String getNotificationContent(String title,String nickname) {
            return title+"에 참가신청이 허락되었습니다.";
        }

        @Override
        public void addScore(Score score) {
        }
    },
    REJECT{
        @Override
        public String getNotificationContent(String title,String nickname) {
            return title+"에 참가신청이 거절되셨습니다.";
        }

        @Override
        public void addScore(Score score) {
        }
    },
    WIN{
        @Override
        public String getNotificationContent(String title,String nickname) {
            return title+" 경기에서 승리하셨습니다.";
        }

        @Override
        public void addScore(Score score) {
            score.addWin();
        }
    },
    LOSE{
        @Override
        public String getNotificationContent(String title,String nickname) {
            return title+" 경기에서 패배하셨습니다.";
        }

        @Override
        public void addScore(Score score) {
            score.addLose();
        }
    },
    DRAW{
        @Override
        public String getNotificationContent(String title,String nickname) {
            return title+" 경기에서 패배하셨습니다.";
        }

        @Override
        public void addScore(Score score) {
            score.addDraw();
        }
    };

    public abstract String getNotificationContent(String title,String nickname);

    public abstract void addScore(Score score);

    @JsonCreator
    public static SubjectEnum from(String name)
    {
        name=name.toUpperCase();
        return SubjectEnum.valueOf(name);
    }
}

