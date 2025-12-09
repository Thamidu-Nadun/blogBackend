package com.nadun.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadun.blog.dto.request.ArticleReqDto;
import com.nadun.blog.dto.response.CategoryResDto;
import com.nadun.blog.model.Category;
import com.nadun.blog.model.Media;
import com.nadun.blog.model.Tags;
import com.nadun.blog.model.content.Article;
import com.nadun.blog.repo.ArticleRepo;
import com.nadun.blog.utils.SlugUtil;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagsService tagsService;
    @Autowired
    private ModelMapper modelMapper;

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

    /**
     * Get article by title
     * 
     * @param title {String}
     * @return Article
     */
    public Article getArticleByTitle(String title) {
        return articleRepo.findByTitle(title);
    }

    /**
     * Get article by slug
     * 
     * @param slug {String}
     * @return Article
     */
    public Article getArticleBySlug(String slug) {
        return articleRepo.findBySlug(slug);
    }

    /**
     * Get articles by published status
     * 
     * @param isPublished {boolean}
     * @return List<Article>
     */
    public List<Article> getArticlesByPublished(boolean isPublished) {
        return articleRepo.findByIsPublished(isPublished);
    }

    /**
     * Get articles by category
     * 
     * @param categoryId {Integer}
     * @return List<Article>
     */
    public List<Article> getArticlesByCategory(Integer categoryId) {
        CategoryResDto category = categoryService.getCategoryById(categoryId);
        return articleRepo.findByCategory(modelMapper.map(category, Category.class));
    }

    // -------------------------------------------------------------------------------

    /**
     * Save article
     * 
     * @param articleDto {ArticleReqDto}
     * @return Article
     */
    public Article saveArticle(ArticleReqDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setPublished(articleDto.isPublished());
        article.setLikes(0);
        article.setViews(0);
        article.setShares(0);

        // 1. Generate slug
        String baseSlug = SlugUtil.toSlug(articleDto.getTitle());
        Integer slugCount = articleRepo.countBySlugLike(baseSlug);
        if (slugCount > 0) {
            baseSlug += "-" + (slugCount + 1);
        }
        article.setSlug(baseSlug);

        // 2. Set media
        Media media = mediaService.saveMedia(articleDto.getCoverImageUrl(), articleDto.getTitle());
        article.setCoverImage(media.getUrl());

        // 3. Set category
        CategoryResDto category = categoryService.getCategoryById(articleDto.getCategoryId());
        article.setCategory(modelMapper.map(category, Category.class));

        // 4. Set tags
        List<Tags> tags = tagsService.saveAllTags(articleDto.getTags());
        article.setTags(tags);

        tags.forEach(tag -> {
            if (tag.getContents() == null) {
                tag.setContents(new ArrayList<>());
            }
            tag.getContents().add(article);
        });

        // 5. Set body
        article.setBody(articleDto.getBody());

        return articleRepo.save(article);
    }

    public List<Article> saveAllArticles(List<ArticleReqDto> articlesDto) {
        List<Article> articles = new ArrayList<>();
        for (ArticleReqDto dto : articlesDto) {
            articles.add(saveArticle(dto));
        }
        return articleRepo.saveAll(articles);
    }
    // -------------------------------------------------------------------------------

    /**
     * Update article
     * 
     * @param id         {Integer}
     * @param articleDto {ArticleReqDto}
     * @return Article
     */
    public Article updateArticle(Integer id, ArticleReqDto articleDto) {
        Article article = articleRepo.findById(id).orElse(null);
        if (article == null) {
            return null;
        }

        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setPublished(articleDto.isPublished());
        article.setLikes(article.getLikes());
        article.setViews(article.getViews());
        article.setShares(article.getShares());

        // Update slug if title has changed
        if (!article.getTitle().equals(articleDto.getTitle())) {
            String baseSlug = SlugUtil.toSlug(articleDto.getTitle());
            Integer slugCount = articleRepo.countBySlugLike(baseSlug);
            if (slugCount > 0) {
                baseSlug += "-" + (slugCount + 1);
            }
            article.setSlug(baseSlug);
        }

        // Update media
        Media media = mediaService.saveMedia(articleDto.getCoverImageUrl(), articleDto.getTitle());
        article.setCoverImage(media.getUrl());

        // Update category
        CategoryResDto category = categoryService.getCategoryById(articleDto.getCategoryId());
        article.setCategory(modelMapper.map(category, Category.class));

        // Update tags
        List<Tags> tags = tagsService.saveAllTags(articleDto.getTags());
        article.setTags(tags);

        // Update body
        article.setBody(articleDto.getBody());

        return articleRepo.save(article);
    }

    // -------------------------------------------------------------------------------

    /**
     * Delete article by id
     * 
     * @param id {Integer}
     */
    public void deleteArticle(Integer id) {
        articleRepo.deleteById(id);
    }

}
