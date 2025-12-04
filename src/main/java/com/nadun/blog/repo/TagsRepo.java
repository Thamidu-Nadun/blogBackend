package com.nadun.blog.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.Tags;

public interface TagsRepo extends JpaRepository<Tags, Integer> {

    List<Tags> findByNameIn(List<String> names);
}
