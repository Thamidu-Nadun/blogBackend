package com.nadun.blog.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    Optional<User> findByUsername(String username);
}
