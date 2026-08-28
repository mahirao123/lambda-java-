package com.springboot.scm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mediaType;

    private String mediaUrl;

    private String cloudinaryId;

    private String caption;

    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
}