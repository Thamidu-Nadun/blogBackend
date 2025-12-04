package com.nadun.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadun.blog.repo.CommentRepo;

@Service
public class CommentService {
    @Autowired
    private CommentRepo commentRepo;

    // TODO: implement comment service methods
}
