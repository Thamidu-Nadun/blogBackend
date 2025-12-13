package com.nadun.blog.model.content;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nadun.blog.model.Category;
import com.nadun.blog.model.Comment;
import com.nadun.blog.model.Tags;
import com.nadun.blog.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Column(unique = true)
    private String slug;
    private String description;
    private String coverImage;
    private boolean published;

    @ManyToOne
    @JsonBackReference
    private User author;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments;

    @ManyToMany(mappedBy = "contents", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Tags> tags;

    @ManyToOne
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "series_id", nullable = true)
    private Series series;

    private Integer views;
    private Integer likes;
    private Integer shares;

}
