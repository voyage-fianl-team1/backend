package com.sparta.matchgi.dto;

import com.sparta.matchgi.model.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class UpdateRequestDto {

    private RequestStatus status;
}
