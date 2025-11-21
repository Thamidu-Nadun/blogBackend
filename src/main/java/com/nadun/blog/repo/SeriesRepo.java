package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.content.Series;

public interface SeriesRepo extends JpaRepository<Series, Integer> {

}
