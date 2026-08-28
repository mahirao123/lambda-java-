package com.springboot.scm.forms;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleForm {

    @NotBlank(message = "Title is required")
    private String title;

    private String shortDescription;

    @NotBlank(message = "Article content is required")
    private String content;

    @NotNull(message = "Category is required")
    private Long categoryId;

    @NotNull(message = "SubCategory is required")
    private Long subCategoryId;

    private String status;

    private List<MultipartFile> images;

    private List<MultipartFile> videos;
}