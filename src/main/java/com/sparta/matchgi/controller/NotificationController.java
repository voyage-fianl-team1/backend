package com.sparta.matchgi.controller;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.NotificationDetailResponseDto;
import com.sparta.matchgi.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/api/users/notifications")
    public ResponseEntity<List<NotificationDetailResponseDto>> getNotice(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.getNotice(userDetails);
    }

    @PutMapping("/api/notifications/{notificationId}")
    public ResponseEntity<?> readProcessing(@PathVariable Long notificationId){
        return notificationService.readProcessing(notificationId);
    }

    @DeleteMapping("/api/notifications/{notificationId}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long notificationId){
        return notificationService.deleteNotice(notificationId);
    }


}
