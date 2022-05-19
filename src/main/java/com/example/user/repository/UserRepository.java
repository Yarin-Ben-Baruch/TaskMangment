package com.example.user.repository;

import com.example.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(long userId);
    Optional<User> findByEmail(String email);
}
