package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.Tags;

public interface TagsRepo extends JpaRepository<Tags, Integer> {

}
