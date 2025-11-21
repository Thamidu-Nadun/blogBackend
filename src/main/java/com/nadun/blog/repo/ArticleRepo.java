package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.content.Article;

public interface ArticleRepo extends JpaRepository<Article, Integer> {

}
