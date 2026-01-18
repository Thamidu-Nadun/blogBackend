package com.nadun.blog.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Reaction {
    private Integer love;
    private Integer fire;
    private Integer thumbsUp;
    private Integer mindBlown;
    private Integer congrats;
    private Integer thumbsDown;
}
