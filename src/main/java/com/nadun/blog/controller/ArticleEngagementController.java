package com.nadun.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nadun.blog.dto.response.ResponseDto;
import com.nadun.blog.service.ArticleEngagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/engagement/articles")
public class ArticleEngagementController {

    @Autowired
    private ArticleEngagementService articleEngagementService;

    @GetMapping("/views/{articleId}")
    public ResponseEntity<ResponseDto> getArticleViews(@PathVariable(required = true) int articleId) {
        try {
            Long articleViews = articleEngagementService.getArticleViews(articleId);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                    "Article views retrieved successfully", articleViews), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/views/{articleId}")
    public ResponseEntity<ResponseDto> updateViews(@PathVariable(required = true) int articleId) {
        try {
            articleEngagementService.incrementArticleViews(articleId);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(), "Views Updated", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
