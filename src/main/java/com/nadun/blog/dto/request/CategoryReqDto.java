package com.nadun.blog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReqDto {
    private String name;
    private String description;
    private String imageUrl;
}
