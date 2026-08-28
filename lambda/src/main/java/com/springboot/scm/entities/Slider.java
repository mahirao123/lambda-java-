package com.springboot.scm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sliders")
public class Slider {

    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private String sliderId;

    private String sliderType;
    
    private String about;

    private String mediaUrl;

    private String thumbnailUrl;

    private String mediaType;
    
    private String bannerText;

    private String cloudinaryId; // Cloudinary public_id

    private boolean active = true;

    // getters setters
}
