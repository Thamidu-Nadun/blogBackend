package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nadun.blog.model.content.Article;
import com.nadun.blog.model.content.Series;

import java.util.List;
import com.nadun.blog.model.Category;

public interface ArticleRepo extends JpaRepository<Article, Integer> {

    @Query("SELECT COUNT(a.slug) FROM Article a WHERE a.slug LIKE ?1%")
    Integer countBySlugLike(String slugPattern);

    Article findBySlug(String slug);

    Article findByTitle(String title);

    List<Article> findByIsPublished(boolean published);

    List<Article> findByCategory(Category category);

    List<Article> findBySeries(Series series);
}
