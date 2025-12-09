package com.nadun.blog.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Tags saveTag(String name) {
        return tagsRepo.save(new Tags(null, name, null));
    }

    /**
     * Save multiple tags | bulk save
     * 
     * @param tags List of Tags
     * @return List of Tags
     */
    public List<Tags> saveAllTags(String names) {
        // 1. Split names by comma
        Set<String> nameSet = Arrays.stream(names.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(java.util.stream.Collectors.toSet());

        // 2. Find existing tags
        List<Tags> existingTags = tagsRepo.findByNameIn(nameSet.stream().toList());
        Set<String> existingTagNames = existingTags.stream()
                .map(Tags::getName)
                .collect(Collectors.toSet());

        // 3. Remove existing tag names from the set
        nameSet.removeAll(existingTagNames);

        // 4. Create new Tags for remaining names
        List<Tags> tags = nameSet.stream()
                .map(name -> new Tags(null, name, null))
                .collect(Collectors.toList());

        // 5. Save new tags to the repository
        List<Tags> savedTags = tagsRepo.saveAll(tags);

        // 6. Combine existing tags with newly saved tags
        existingTags.addAll(savedTags);

        return existingTags;
    }

    /**
     * Update a tag
     * 
     * @param name {String}
     * @return Tags
     */
    public Tags updateTag(Integer id, String name) {
        Tags tag = tagsRepo.findById(id).orElse(null);
        if (tag != null) {
            tag.setName(name);
            return tagsRepo.save(tag);
        }
        return null;
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
