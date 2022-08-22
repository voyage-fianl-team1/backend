package com.sparta.matchgi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyMatchResponseDto {
    private List<MyMatchDetailResponseDto> mymatchList;
}
