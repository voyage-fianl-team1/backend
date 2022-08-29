package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.Notification;
import com.sparta.matchgi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findAllByPost(Post post);
}
