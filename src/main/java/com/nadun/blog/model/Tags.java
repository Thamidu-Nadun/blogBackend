package com.nadun.blog.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nadun.blog.model.content.Content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JsonBackReference
    @JsonIgnore
    @JoinTable(name = "content_tags", joinColumns = @jakarta.persistence.JoinColumn(name = "tag_id"), inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "content_id"))
    private List<Content> contents;
}
