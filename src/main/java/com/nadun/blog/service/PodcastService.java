package com.nadun.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadun.blog.repo.PodcastRepo;

@Service
public class PodcastService {
    @Autowired
    private PodcastRepo podcastRepo;
}
