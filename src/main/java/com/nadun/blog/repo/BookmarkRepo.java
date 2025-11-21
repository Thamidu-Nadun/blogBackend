package com.nadun.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nadun.blog.model.Bookmark;

public interface BookmarkRepo extends JpaRepository<Bookmark, Integer> {

}
