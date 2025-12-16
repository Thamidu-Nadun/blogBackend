package com.nadun.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nadun.blog.dto.request.ArticleReqDto;
import com.nadun.blog.dto.response.ArticleResDto;
import com.nadun.blog.dto.response.AuthorDto;
import com.nadun.blog.dto.response.CategoryResDto;
import com.nadun.blog.model.Category;
import com.nadun.blog.model.Media;
import com.nadun.blog.model.Tags;
import com.nadun.blog.model.User;
import com.nadun.blog.model.content.Article;
import com.nadun.blog.repo.ArticleRepo;
import com.nadun.blog.repo.UserRepo;
import com.nadun.blog.utils.SlugUtil;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private UserRepo userRepo;

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
    public List<ArticleResDto> getArticles() {
        return articleRepo.findAll().stream().map(article -> {
            ArticleResDto dto = new ArticleResDto();
            dto.setId(article.getId());
            dto.setTitle(article.getTitle());
            dto.setSlug(article.getSlug());
            dto.setDescription(article.getDescription());
            dto.setCoverImage(article.getCoverImage());
            dto.setPublished(article.isPublished());
            dto.setBody(article.getBody());

            User author = article.getAuthor();
            if (author != null)
                dto.setAuthorId(new AuthorDto(author.getId(), author.getName()));

            Category category = article.getCategory();
            if (category != null)
                dto.setCategory(modelMapper.map(article.getCategory(), CategoryResDto.class));

            dto.setTags(article.getTags());
            dto.setViews(article.getViews());
            dto.setLikes(article.getLikes());
            dto.setShares(article.getShares());
            return dto;
        }).toList();
    }

    /**
     * Get article by id
     * 
     * @param id {Integer}
     * @return Article
     */
    public ArticleResDto getArticleById(Integer id) {
        Article article = articleRepo.findById(id).orElse(null);
        if (article == null) {
            return null;
        }
        ArticleResDto dto = new ArticleResDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setSlug(article.getSlug());
        dto.setDescription(article.getDescription());
        dto.setCoverImage(article.getCoverImage());
        dto.setPublished(article.isPublished());
        dto.setBody(article.getBody());

        User author = article.getAuthor();
        if (author != null)
            dto.setAuthorId(new AuthorDto(author.getId(), author.getName()));

        Category category = article.getCategory();
        if (category != null)
            dto.setCategory(modelMapper.map(article.getCategory(), CategoryResDto.class));

        dto.setTags(article.getTags());
        dto.setViews(article.getViews());
        dto.setLikes(article.getLikes());
        dto.setShares(article.getShares());
        return dto;
    }

    /**
     * Get article by title
     * 
     * @param title {String}
     * @return Article
     */
    public ArticleResDto getArticleByTitle(String title) {
        Article article = articleRepo.findByTitle(title);
        if (article == null) {
            return null;
        }
        ArticleResDto dto = new ArticleResDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setSlug(article.getSlug());
        dto.setDescription(article.getDescription());
        dto.setCoverImage(article.getCoverImage());
        dto.setPublished(article.isPublished());
        dto.setBody(article.getBody());

        User author = article.getAuthor();
        if (author != null)
            dto.setAuthorId(new AuthorDto(author.getId(), author.getName()));

        Category category = article.getCategory();
        if (category != null)
            dto.setCategory(modelMapper.map(article.getCategory(), CategoryResDto.class));

        dto.setTags(article.getTags());
        dto.setViews(article.getViews());
        dto.setLikes(article.getLikes());
        dto.setShares(article.getShares());
        return dto;
    }

    /**
     * Get article by slug
     * 
     * @param slug {String}
     * @return Article
     */
    public ArticleResDto getArticleBySlug(String slug) {
        Article article = articleRepo.findBySlug(slug);
        if (article == null) {
            return null;
        }
        ArticleResDto dto = new ArticleResDto();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setSlug(article.getSlug());
        dto.setDescription(article.getDescription());
        dto.setCoverImage(article.getCoverImage());
        dto.setPublished(article.isPublished());
        dto.setBody(article.getBody());
        User author = article.getAuthor();
        if (author != null)
            dto.setAuthorId(new AuthorDto(author.getId(), author.getName()));
        Category category = article.getCategory();
        if (category != null)
            dto.setCategory(modelMapper.map(article.getCategory(), CategoryResDto.class));

        dto.setTags(article.getTags());
        dto.setViews(article.getViews());
        dto.setLikes(article.getLikes());
        dto.setShares(article.getShares());
        return dto;
    }

    /**
     * Get articles by published status
     * 
     * @param isPublished {boolean}
     * @return List<Article>
     */
    public List<Article> getArticlesByPublished(boolean isPublished) {
        return articleRepo.findByPublished(isPublished);
    }

    /**
     * Get latest articles
     * 
     * @return List<Article>
     */
    public Page<Article> getLatestArticles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepo.getLatestArticles(pageable);
    }

    /**
     * Get popular articles
     * 
     * @return List<Article>
     */
    public Page<Article> getPopularArticles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return articleRepo.getPopularArticles(pageable);
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

        User author = userRepo.findById(articleDto.getAuthorId()).orElse(null);

        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setPublished(articleDto.isPublished());
        article.setLikes(0);
        article.setViews(0);
        article.setShares(0);
        if (author != null) {
            article.setAuthor(author);
        }

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
        if (category != null) {
            article.setCategory(modelMapper.map(category, Category.class));
        }

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
        article.setAuthor(article.getAuthor());
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
        if (category != null) {
            article.setCategory(modelMapper.map(category, Category.class));
        } else {
            article.setCategory(article.getCategory());
        }

        // Update tags
        List<Tags> tags = tagsService.saveAllTags(articleDto.getTags());
        article.getTags().clear();
        article.getTags().addAll(tags);

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
