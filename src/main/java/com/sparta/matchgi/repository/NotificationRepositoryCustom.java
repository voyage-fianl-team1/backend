package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.NotificationDetailResponseDto;
import com.sparta.matchgi.model.User;

import java.util.List;

public interface NotificationRepositoryCustom {
    List<NotificationDetailResponseDto> getNotice(User user);
}
