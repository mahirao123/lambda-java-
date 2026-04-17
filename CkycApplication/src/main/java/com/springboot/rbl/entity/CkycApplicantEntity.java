package com.springboot.rbl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CkycApplicantEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String dob;
    private String gender;
    private String pan;
    private String mobile;
    private String ckycNumber;
    private String status;
}

