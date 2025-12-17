package com.nadun.blog.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadun.blog.dto.request.CategoryReqDto;
import com.nadun.blog.dto.response.CategoryResDto;
import com.nadun.blog.model.Category;
import com.nadun.blog.model.content.Article;
import com.nadun.blog.repo.ArticleRepo;
import com.nadun.blog.repo.CategoryRepo;
import com.nadun.blog.utils.SlugUtil;

@Service
public class CategoryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ArticleRepo articleRepo;

    /**
     * Get all categories
     * 
     * @return List of categories
     */
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    /**
     * Get category by id
     * 
     * @param id Category id
     * @return Category
     */
    public CategoryResDto getCategoryById(Integer id) {
        Category category = categoryRepo.findById(id).orElse(null);
        if (category == null) {
            return null;
        }
        int post_count = categoryRepo.countPostsByCategoryId(id);
        return new CategoryResDto(
                category.getId().longValue(),
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getImageUrl(),
                post_count);
    }

    /**
     * Create or update a category
     * 
     * @param category Category to be created or updated
     * @return Created or updated category
     */
    public Category saveCategory(CategoryReqDto dto) {
        return categoryRepo.save(new Category(null, dto.getName(), dto.getDescription(), dto.getImageUrl(),
                SlugUtil.toSlug(dto.getName())));
    }

    /**
     * Save multiple categories from a comma-separated string
     * 
     * @param categories Comma-separated category names
     * @return List of saved categories
     */
    public List<Category> saveAllCategories(List<CategoryReqDto> categories) {
        List<Category> categoryList = categories.stream()
                .map(dto -> new Category(null, dto.getName(), dto.getDescription(), dto.getImageUrl(),
                        SlugUtil.toSlug(dto.getName())))
                .toList();
        return categoryRepo.saveAll(categoryList);
    }

    /**
     * Update category by id
     * 
     * @param id             Category id
     * @param CategoryReqDto dto Category data to update
     * @return Updated category
     */
    public Category updateCategory(Integer id, CategoryReqDto dto) {
        Category category = categoryRepo.findById(id).orElse(null);
        if (category != null) {
            category.setName(dto.getName());
            category.setDescription(dto.getDescription());
            category.setImageUrl(dto.getImageUrl());
            category.setSlug(SlugUtil.toSlug(dto.getName()));
            return categoryRepo.save(category);
        }
        return null;
    }

    /**
     * Delete category by id
     * 
     * @param id Category id
     */
    public void deleteCategoryById(Integer id) {
        Category category = categoryRepo.findById(id).orElse(null);
        if (category == null) {
            return;
        }

        List<Article> article = articleRepo.findByCategory(category);
        for (Article a : article) {
            a.setCategory(null);
        }
        articleRepo.saveAll(article);
        categoryRepo.deleteById(id);
    }

}
