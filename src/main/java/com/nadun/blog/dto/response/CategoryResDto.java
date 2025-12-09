package com.nadun.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private Integer postCount;
}
