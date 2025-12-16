package com.nadun.blog.controller;

import java.util.List;
import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.TypeToken;

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
            List<ArticleResDto> _articles = articleService.getArticles();

            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), _articles), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/published")
    public ResponseEntity<ResponseDto> getPublishedArticles(@RequestParam boolean published) {
        try {
            List<Article> _articles = articleService.getArticlesByPublished(published);
            if (_articles != null && _articles.isEmpty()) {
                return new ResponseEntity<>(new ResponseDto(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null), HttpStatus.NOT_FOUND);
            }

            Type listType = new TypeToken<List<ArticleResDto>>() {
            }.getType();
            List<ArticleResDto> articles = mapper.map(_articles, listType);

            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), articles), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<ResponseDto> getLatestArticles(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            Page<Article> _articles = articleService.getLatestArticles(page, size);
            if (_articles != null && _articles.isEmpty()) {
                return new ResponseEntity<>(new ResponseDto(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null), HttpStatus.NOT_FOUND);
            }

            Type listType = new TypeToken<Page<ArticleResDto>>() {
            }.getType();
            Page<ArticleResDto> articles = mapper.map(_articles, listType);

            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), articles), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/popular")
    public ResponseEntity<ResponseDto> getPopularArticles() {
        try {
            List<Article> _articles = articleService.getPopularArticles();
            if (_articles != null && _articles.isEmpty()) {
                return new ResponseEntity<>(new ResponseDto(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null), HttpStatus.NOT_FOUND);
            }

            Type listType = new TypeToken<List<ArticleResDto>>() {
            }.getType();
            List<ArticleResDto> articles = mapper.map(_articles, listType);

            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), articles), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getArticleById(@PathVariable Integer id) {
        try {
            ArticleResDto article = articleService.getArticleById(id);
            if (article == null)
                return new ResponseEntity<>(new ResponseDto(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null), HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(), article), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/title")
    public ResponseEntity<ResponseDto> getArticleByTitle(@RequestParam String title) {
        try {
            ArticleResDto article = articleService.getArticleByTitle(title);
            if (article == null)
                return new ResponseEntity<>(new ResponseDto(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null), HttpStatus.NOT_FOUND);
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
            ArticleResDto article = articleService.getArticleBySlug(slug);
            if (article == null)
                return new ResponseEntity<>(new ResponseDto(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null), HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto> updateArticle(@PathVariable Integer id,
            @RequestBody ArticleReqDto articleReqDto) {
        try {
            Article _updatedArticle = articleService.updateArticle(id, articleReqDto);
            if (_updatedArticle == null) {
                return new ResponseEntity<>(new ResponseDto(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
                        null), HttpStatus.NOT_FOUND);
            }
            ArticleResDto updatedArticle = mapper.map(_updatedArticle, ArticleResDto.class);
            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), updatedArticle),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
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
