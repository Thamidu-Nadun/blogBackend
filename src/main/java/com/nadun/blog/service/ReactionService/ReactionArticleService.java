package com.nadun.blog.service.ReactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadun.blog.model.Reaction;
import com.nadun.blog.repo.ArticleRepo;
import com.nadun.blog.utils.ReactionType;

@Service
public class ReactionArticleService {
    @Autowired
    private ArticleRepo articleRepo;

    /**
     * Get Reactions for an Article
     * 
     * @param articleId
     * @return Reaction
     */
    public Reaction getArticleReactions(Integer articleId) {
        return articleRepo.findById(articleId).orElse(null).getReactions();
    }

    /**
     * Update Reactions for an Article
     * 
     * @param articleId
     * @param reactions
     */
    public void updateArticleReactions(Integer articleId, Reaction reactions) {
        var article = articleRepo.findById(articleId).orElse(null);
        if (article == null) {
            return;
        }
        article.setReactions(reactions);
        articleRepo.save(article);
    }

    /**
     * Increment Reaction Count for an Article
     * 
     * @param articleId
     * @param type
     */
    public void incrementReaction(Integer articleId, ReactionType type) {
        var article = articleRepo.findById(articleId).orElse(null);
        if (article == null) {
            return;
        }
        if (type == null) {
            return;
        }
        var reactions = article.getReactions();
        switch (type) {
            case LOVE:
                reactions.setLove(reactions.getLove() + 1);
                break;
            case FIRE:
                reactions.setFire(reactions.getFire() + 1);
                break;
            case THUMBS_UP:
                reactions.setThumbsUp(reactions.getThumbsUp() + 1);
                break;
            case MIND_BLOWN:
                reactions.setMindBlown(reactions.getMindBlown() + 1);
                break;
            case CONGRATS:
                reactions.setCongrats(reactions.getCongrats() + 1);
                break;
            case THUMBS_DOWN:
                reactions.setThumbsDown(reactions.getThumbsDown() + 1);
                break;
            default:
                break;
        }
        article.setReactions(reactions);
        articleRepo.save(article);
    }

}
