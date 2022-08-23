package com.sparta.matchgi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum SubjectEnum {
    SOCCER,
    BASKETBALL,
    TENNIS,
    BADMINTON,
    BILLIARDS,
    BOWLING,

    ETC;



    @JsonCreator
    public static SubjectEnum from(String name)
    {
        name=name.toUpperCase();
        return SubjectEnum.valueOf(name);
    }
}
