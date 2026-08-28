package com.springboot.scm.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.scm.entities.Article;
import com.springboot.scm.repositories.ArticleRepository;
import com.springboot.scm.services.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepo;
	
	@Override
	public Article saveArticle(Article article) {
		
		return articleRepo.save(article);
	}

	@Override
	public Article updateArticle(Article article) {
		
		return articleRepo.save(article);
	}

	@Override
	public Optional<Article> getByArticleId(String categoryId) {
		
		return articleRepo.findByArticleId(categoryId);
	}

	@Override
	public List<Article> getAllArticles() {
		
		return articleRepo.findAll();
	}

}
