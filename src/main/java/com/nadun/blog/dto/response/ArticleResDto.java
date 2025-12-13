package com.nadun.blog.dto.response;

import java.util.List;

import com.nadun.blog.model.Tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResDto {
    private Integer id;
    private String title;
    private String slug;
    private String description;
    private String coverImage;
    private boolean isPublished;
    private String body;
    private AuthorDto authorId;
    private CategoryResDto category;
    private List<Tags> tags;
    private Integer views;
    private Integer likes;
    private Integer shares;
}
