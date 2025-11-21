package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.content.Podcast;

public interface PodcastRepo extends JpaRepository<Podcast, Integer> {

}
