package com.nadun.blog.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nadun.blog.dto.request.ArticleReqDto;
import com.nadun.blog.dto.response.ArticleResDto;
import com.nadun.blog.dto.response.ResponseDto;
import com.nadun.blog.model.content.Article;
import com.nadun.blog.service.ArticleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping({ "", "/" })
    public ResponseEntity<ResponseDto> getArticles() {
        try {
            List<Article> _articles = articleService.getArticles();

            List<ArticleResDto> articles = _articles.stream().map(
                    article -> mapper.map(article, ArticleResDto.class)).toList();

            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), articles), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getArticleById(@PathVariable Integer id) {
        try {
            Article _article = articleService.getArticleById(id);
            if (_article == null)
                return new ResponseEntity<>(new ResponseDto(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null), HttpStatus.NOT_FOUND);
            ArticleResDto article = mapper.map(
                    _article, ArticleResDto.class);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(), article), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/title")
    public ResponseEntity<ResponseDto> getArticleByTitle(@RequestParam String title) {
        try {
            Article _article = articleService.getArticleByTitle(title);
            if (_article == null)
                return new ResponseEntity<>(new ResponseDto(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null), HttpStatus.NOT_FOUND);
            ArticleResDto article = mapper.map(
                    _article, ArticleResDto.class);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(), article), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/slug")
    public ResponseEntity<ResponseDto> getArticleBySlug(@RequestParam String slug) {
        try {
            Article _article = articleService.getArticleBySlug(slug);
            if (_article == null)
                return new ResponseEntity<>(new ResponseDto(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null), HttpStatus.NOT_FOUND);
            ArticleResDto article = mapper.map(_article, ArticleResDto.class);
            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), article), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<ResponseDto> getArticlesByCategory(@RequestParam Integer catId) {
        try {
            List<Article> _articles = articleService.getArticlesByCategory(catId);

            List<ArticleResDto> articles = _articles.stream().map(
                    article -> mapper.map(article, ArticleResDto.class)).toList();

            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), articles), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveArticle(@RequestBody ArticleReqDto articleReqDto) {
        try {
            Article _savedArticle = articleService.saveArticle(articleReqDto);
            ArticleResDto savedArticle = mapper.map(_savedArticle, ArticleResDto.class);
            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase(), savedArticle),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto> updateArticle(@PathVariable Integer id,
            @RequestBody ArticleReqDto articleReqDto) {
        try {
            Article _updatedArticle = articleService.updateArticle(id, articleReqDto);
            ArticleResDto updatedArticle = mapper.map(_updatedArticle, ArticleResDto.class);
            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), updatedArticle),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteArticle(@PathVariable Integer id) {
        try {
            articleService.deleteArticle(id);
            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), "Article deleted successfully", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
