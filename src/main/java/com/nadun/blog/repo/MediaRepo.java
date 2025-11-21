package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.Media;

public interface MediaRepo extends JpaRepository<Media, Integer> {

}
