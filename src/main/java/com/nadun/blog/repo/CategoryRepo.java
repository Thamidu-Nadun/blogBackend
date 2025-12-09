package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nadun.blog.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    @Query("SELECT COUNT(p) FROM Article p WHERE p.category.id = ?1")
    int countPostsByCategoryId(Integer id);
}
