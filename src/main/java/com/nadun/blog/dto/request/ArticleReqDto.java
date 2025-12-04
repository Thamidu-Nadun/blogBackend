package com.nadun.blog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReqDto {
    private String title;
    private String description;
    private String coverImageUrl;
    private boolean isPublished;
    private Integer categoryId;
    private String tags;
    private String body;
}
