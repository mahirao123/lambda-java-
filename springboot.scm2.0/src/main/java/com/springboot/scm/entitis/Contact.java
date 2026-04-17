package com.springboot.scm.entitis;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
	
	@Id
	private String id;
	
	private String name;
	private String address;
	private String email;
	private String phoneNumber;
	private String picture;
	private String description;
	
	private boolean favorite = false;
	
	private String websiteLink;
	private String linkedInLink;
//	private List<SocialLink> socialLinks = new ArrayList<>();
	
	private String cloudinaryImagePublicId;
	
	@ManyToOne
	private User user;

    @OneToMany(mappedBy="contact",cascade=CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    private List<SocialLink>socialLinks=new ArrayList<>();
	
}
