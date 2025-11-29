package com.nadun.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nadun.blog.dto.response.ResponseDto;
import com.nadun.blog.model.Category;
import com.nadun.blog.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping({ "", "/" })
    public ResponseEntity<ResponseDto> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(
                    new ResponseDto(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase(),
                            categories),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/:id")
    public ResponseEntity<ResponseDto> getCategoryByName(@PathVariable Integer id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(
                    new ResponseDto(
                            HttpStatus.FOUND.value(),
                            HttpStatus.FOUND.getReasonPhrase(),
                            category),
                    HttpStatus.FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveCategory(@RequestBody(required = true) String name) {
        try {
            Category savedCategory = categoryService.saveCategory(name);
            return new ResponseEntity<>(
                    new ResponseDto(
                            HttpStatus.CREATED.value(),
                            HttpStatus.CREATED.getReasonPhrase(),
                            savedCategory),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
