package com.nadun.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadun.blog.model.content.Article;
import com.nadun.blog.repo.ArticleRepo;

import lombok.NonNull;

@Service
public class ArticleEngagementService {
    @Autowired
    private ArticleRepo articleRepo;

    /**
     * Returns the number of views for a given article.
     * 
     * @param articleId
     * @return views
     */
    public Long getArticleViews(@NonNull Integer articleId) {
        Article article = articleRepo.findById(articleId).orElse(null);
        if (article == null) {
            return 0L;
        }

        return article.getViews();
    }

    /**
     * Update Article Views
     * 
     * @param articleId
     */
    public void incrementArticleViews(@NonNull Integer articleId) {
        Article article = articleRepo.findById(articleId).orElse(null);
        if (article == null) {
            return;
        }
        article.setViews(article.getViews() + 1);
        articleRepo.save(article);
    }
}
