package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
