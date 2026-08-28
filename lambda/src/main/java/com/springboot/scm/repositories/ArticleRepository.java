package com.springboot.scm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.scm.entities.Article;

public interface ArticleRepository
        extends JpaRepository<Article, Long> {

    Optional<Article> findByArticleId(String articleId);

    Optional<Article> findBySlug(String slug);
}