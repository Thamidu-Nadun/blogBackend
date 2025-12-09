package com.nadun.blog.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nadun.blog.model.content.Content;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String body;
    private Date createdAt;

    @ManyToOne
    @JsonBackReference
    private User author;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "content_id")
    private Content content;
}
