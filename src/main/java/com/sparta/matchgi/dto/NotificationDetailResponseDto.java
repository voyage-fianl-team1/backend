package com.sparta.matchgi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NotificationDetailResponseDto {
    private Long postId;
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private boolean isread;
}
