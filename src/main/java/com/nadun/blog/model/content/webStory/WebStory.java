package com.nadun.blog.model.content.webStory;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nadun.blog.model.content.Content;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class WebStory extends Content {
    @OneToMany(mappedBy = "webStory", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<WebSlide> slides;
}
