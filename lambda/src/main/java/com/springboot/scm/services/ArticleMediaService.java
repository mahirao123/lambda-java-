package com.springboot.scm.services;

import java.util.List;
import java.util.Optional;

import com.springboot.scm.entities.ArticleMedia;

public interface ArticleMediaService {
	
	ArticleMedia saveArticleMedia(ArticleMedia articleMedia);
	
	ArticleMedia updateArticleMedia(ArticleMedia articleMedia);
	
	Optional<ArticleMedia> getArticleMediaById(Long id);
	
	List<ArticleMedia> getAllArticles();

}
