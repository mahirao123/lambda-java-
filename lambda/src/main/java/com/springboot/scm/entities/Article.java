package com.springboot.scm.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.springboot.scm.employeeEntities.EmployeeDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

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
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String articleId;

    private String title;

    private String slug;

    private String shortDescription;

    private String content;

    private String status;

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    // Category/SubCategory

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id", nullable = false)
    private ContentSubCategory subCategory;


    // Author / Employee

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = true)
    private EmployeeDetails author;


    // Article media

    @Builder.Default
    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ArticleMedia> media = new ArrayList<>();
}