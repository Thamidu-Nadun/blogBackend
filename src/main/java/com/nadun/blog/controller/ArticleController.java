package com.nadun.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nadun.blog.dto.response.ResponseDto;
import com.nadun.blog.model.content.Article;
import com.nadun.blog.service.ArticleService;

import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping({ "", "/" })
    public ResponseEntity<ResponseDto> getArticles() {
        try {
            List<Article> articles = articleService.getArticles();
            return new ResponseEntity<>(
                    new ResponseDto(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), articles), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
