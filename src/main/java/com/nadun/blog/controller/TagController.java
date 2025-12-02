package com.nadun.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nadun.blog.dto.response.ResponseDto;
import com.nadun.blog.model.Tags;
import com.nadun.blog.service.TagsService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    @Autowired
    private TagsService tagsService;

    @GetMapping({ "/", "" })
    public ResponseEntity<ResponseDto> getAllTags() {
        try {
            List<Tags> tags = tagsService.getAllTags();
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.OK.value(),
                    "Tags fetched successfully",
                    tags), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while fetching tags: " + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getTagById(@PathVariable Integer id) {
        try {
            Tags tag = tagsService.getTagById(id);
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.OK.value(),
                    "Tag fetched successfully",
                    tag), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while fetching the tag: " + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> saveTag(@RequestBody Map<String, String> req) {
        try {
            Tags tag = tagsService.saveTag(req.get("name"));
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.CREATED.value(),
                    "Tag saved successfully",
                    tag), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while saving the tag: " + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save/bulk")
    public ResponseEntity<ResponseDto> saveAllTags(@RequestBody Map<String, String> req) {
        try {
            List<Tags> savedTags = tagsService.saveAllTags(req.get("names"));
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.CREATED.value(),
                    "Tags saved successfully",
                    savedTags), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while saving the tags: " + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto> updateTag(@PathVariable Integer id, @RequestBody Map<String, String> req) {
        try {
            Tags tag = tagsService.updateTag(id, req.get("name"));
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.OK.value(),
                    "Tag updated successfully",
                    tag), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while updating the tag: " + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteTag(@PathVariable Integer id) {
        try {
            tagsService.deleteTagById(id);
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.NO_CONTENT.value(),
                    "Tag deleted successfully",
                    null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while deleting the tag: " + e.getMessage(),
                    null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
