package com.sparta.matchgi.dto;

import com.sparta.matchgi.model.RequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UpdateRequestDto {

    private RequestStatus status;
}
