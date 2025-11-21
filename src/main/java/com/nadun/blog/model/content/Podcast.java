package com.nadun.blog.model.content;

import com.nadun.blog.model.Media;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Podcast extends Content {
    @OneToOne
    @JoinColumn(name = "audio_id", referencedColumnName = "id", nullable = false)
    private Media audio;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String transcript;
}
