package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
