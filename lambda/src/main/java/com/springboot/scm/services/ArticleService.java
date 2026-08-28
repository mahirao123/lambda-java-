package com.springboot.scm.services;

import java.util.List;
import java.util.Optional;

import com.springboot.scm.entities.Article;

public interface ArticleService {
	
	Article saveArticle(Article article);
	
	Article updateArticle(Article article);
	
	Optional<Article> getByArticleId(String categoryId);
	
    List<Article> getAllArticles();
}
