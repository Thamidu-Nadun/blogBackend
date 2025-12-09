package com.nadun.blog.model.content.webStory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nadun.blog.model.Media;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WebSlide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String caption;
    private Integer order;

    @OneToOne
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    @ManyToOne
    @JoinColumn(name = "web_story_id", nullable = false)
    @JsonBackReference
    private WebStory webStory;
}
