package com.springboot.scm.entitis;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Complain {

@Id
private String complainId;

private String title;

private String complainUrl;

private String complainResultUrl;


}
