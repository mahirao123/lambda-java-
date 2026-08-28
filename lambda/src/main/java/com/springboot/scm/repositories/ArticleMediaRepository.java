package com.springboot.scm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.scm.entities.ArticleMedia;

public interface ArticleMediaRepository
        extends JpaRepository<ArticleMedia, Long> {

    List<ArticleMedia> findByArticleIdOrderByDisplayOrderAsc(Long articleId);
}