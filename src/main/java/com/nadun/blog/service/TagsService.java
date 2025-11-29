package com.nadun.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadun.blog.model.Tags;
import com.nadun.blog.repo.TagsRepo;

@Service
public class TagsService {
    @Autowired
    private TagsRepo tagsRepo;

    /**
     * Get all tags
     * 
     * @return List of Tags
     */
    public List<Tags> getAllTags() {
        return tagsRepo.findAll();
    }

    /**
     * Get tag by id
     * 
     * @param id Tag id
     * @return Tags
     */
    public Tags getTagById(Integer id) {
        return tagsRepo.findById(id).orElse(null);
    }

    /**
     * Save a tag
     * 
     * @param tag Tags
     * @return Tags
     */
    public Tags saveTag(Tags tag) {
        return tagsRepo.save(tag);
    }

    /**
     * Delete tag by id
     * 
     * @param id Tag id
     */
    public void deleteTagById(Integer id) {
        tagsRepo.deleteById(id);
    }

}
