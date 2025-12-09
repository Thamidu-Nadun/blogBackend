package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
