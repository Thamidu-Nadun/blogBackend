package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.content.webStory.WebStory;

public interface WebStoryRepo extends JpaRepository<WebStory, Integer> {

}
