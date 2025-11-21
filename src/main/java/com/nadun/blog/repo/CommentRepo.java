package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
