package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.Date;
import java.util.List;

public interface ArticleRepository {
    Article find(long id);
    void save(Article article);
    void updateHidden(long articleId, boolean hidden);
    Date getCurrentTime();
    List<Article> findAll();
    List<Article> findById(long id);
}
