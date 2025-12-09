package com.nadun.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadun.blog.model.Media;
import com.nadun.blog.repo.MediaRepo;

@Service
public class MediaService {
    @Autowired
    private MediaRepo mediaRepo;

    /**
     * Get all media
     * 
     * @return List of media
     */
    public List<Media> getAllMedia() {
        return mediaRepo.findAll();
    }

    /**
     * Get media by id
     * 
     * @param id Media id
     * @return Media
     */
    public Media getMediaById(Integer id) {
        return mediaRepo.findById(id).orElse(null);
    }

    /**
     * Create or update media
     * 
     * @param media Media to be created or updated
     * @return Created or updated media
     */
    public Media saveMedia(String url, String altText) {
        return mediaRepo.save(new Media(null, url, altText));
    }

    /**
     * Delete media by id
     * 
     * @param id Media id
     */
    public void deleteMediaById(Integer id) {
        mediaRepo.deleteById(id);
    }
}
