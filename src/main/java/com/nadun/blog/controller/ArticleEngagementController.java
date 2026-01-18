package com.nadun.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nadun.blog.dto.response.ResponseDto;
import com.nadun.blog.service.ArticleEngagementService;
import com.nadun.blog.service.ReactionService.ReactionArticleService;
import com.nadun.blog.utils.ReactionType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/engagement/articles")
public class ArticleEngagementController {

    @Autowired
    private ArticleEngagementService articleEngagementService;

    @Autowired
    private ReactionArticleService reactionArticleService;

    // ---------------- Views Endpoints ----------------

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

    // ---------------- Reaction Endpoints ----------------

    @GetMapping("/reactions/{articleId}")
    public ResponseEntity<ResponseDto> getArticleReactions(@PathVariable(required = true) int articleId) {
        try {
            var reactions = reactionArticleService.getArticleReactions(articleId);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(),
                    "Article reactions retrieved successfully", reactions), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reactions/{articleId}")
    public ResponseEntity<ResponseDto> updateArticleReactions(@PathVariable(required = true) int articleId) {
        try {
            var reactions = reactionArticleService.getArticleReactions(articleId);
            reactionArticleService.updateArticleReactions(articleId, reactions);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(), "Article reactions updated", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/reactions/engage/{id}")
    public ResponseEntity<ResponseDto> engageReaction(@RequestBody ReactionType type,
            @PathVariable(required = true) int id) {
        try {
            reactionArticleService.incrementReaction(id, type);
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.value(), "Reaction engaged", null),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
