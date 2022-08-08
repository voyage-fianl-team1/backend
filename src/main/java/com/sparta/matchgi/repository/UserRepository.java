package com.sparta.matchgi.repository;

import com.sparta.matchgi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);


}
