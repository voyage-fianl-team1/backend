package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.GetScoresResponseDto;
import com.sparta.matchgi.model.RequestStatus;
import com.sparta.matchgi.model.User;

import java.util.List;

public interface RequestRepositoryCustom {

    List<GetScoresResponseDto> ScoresSubject(User user, String subject, List<RequestStatus>requestStatusList);
}
