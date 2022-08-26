package com.sparta.matchgi.service;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.NotificationDetailResponseDto;
import com.sparta.matchgi.model.Notification;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.repository.NotificationRepository;
import com.sparta.matchgi.repository.NotificationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationRepositoryImpl notificationRepositoryImpl;


    public ResponseEntity<List<NotificationDetailResponseDto>> getNotice(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return new ResponseEntity<>(notificationRepositoryImpl.getNotice(user), HttpStatus.valueOf(200));
    }

    public ResponseEntity<?> readProcessing(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(
                () -> new IllegalArgumentException("없는 알림입니다.")
        );

        notification.change();
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    public ResponseEntity<?> deleteNotice(Long notificationId) {

        notificationRepository.deleteById(notificationId);

        return new ResponseEntity<>("",HttpStatus.OK);
    }
}
