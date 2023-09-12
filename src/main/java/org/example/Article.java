package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Article {
    private String title;
    private String url;
    private String content;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Boolean isUse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(title, article.title);
    }
}
