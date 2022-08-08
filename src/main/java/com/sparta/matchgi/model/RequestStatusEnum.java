package com.sparta.matchgi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum RequestStatusEnum {
    PENDING,
    FUIFILED,
    WIN,
    LOSE,
    DRAW;

    @JsonCreator
    public static SubjectEnum from(String name)
    {
        name=name.toUpperCase();
        return SubjectEnum.valueOf(name);
    }
}

