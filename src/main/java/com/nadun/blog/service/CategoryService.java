package com.nadun.blog.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadun.blog.model.Category;
import com.nadun.blog.repo.CategoryRepo;
import com.nadun.blog.utils.SlugUtil;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

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
    public Category getCategoryById(Integer id) {
        return categoryRepo.findById(id).orElse(null);
    }

    /**
     * Create or update a category
     * 
     * @param category Category to be created or updated
     * @return Created or updated category
     */
    public Category saveCategory(String name) {
        return categoryRepo.save(new Category(null, name, SlugUtil.toSlug(name), null));
    }

    public List<Category> saveAllCategories(String categories) {
        List<Category> categoryList = Arrays.stream(categories.split(","))
                .map(name -> new Category(null, name.trim(), SlugUtil.toSlug(name.trim()), null))
                .toList();
        return categoryRepo.saveAll(categoryList);
    }

    /**
     * Update category by id
     * 
     * @param id   Category id
     * @param name New category name
     * @return Updated category
     */
    public Category updateCategory(Integer id, String name) {
        Category category = getCategoryById(id);
        if (category != null) {
            category.setName(name);
            category.setSlug(SlugUtil.toSlug(name));
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
        categoryRepo.deleteById(id);
    }

}
