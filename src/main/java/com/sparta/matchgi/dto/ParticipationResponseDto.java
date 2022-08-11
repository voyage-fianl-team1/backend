package com.sparta.matchgi.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParticipationResponseDto {

    private List<RequestResponseDto> userList;

}
