package com.nadun.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadun.blog.model.content.Article;
import com.nadun.blog.repo.ArticleRepo;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    /**
     * Get all articles
     * 
     * @return List<Article>
     */
    public List<Article> getArticles() {
        return articleRepo.findAll();
    }

    /**
     * Get article by id
     * 
     * @param id {Integer}
     * @return Article
     */
    public Article getArticleById(Integer id) {
        return articleRepo.findById(id).orElse(null);
    }
}
