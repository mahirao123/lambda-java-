package com.springboot.scm.employeeEntitis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.scm.entitis.SocialMediaUrls;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetails
        implements UserDetails {

    @Id
    private String employeeId;

    @Column(name = "employee_name")
    private String name;

    @Column(
            unique = true,
            nullable = false)
    private String email;

    @Getter(AccessLevel.NONE)
    private String password;

    @Column(length = 1000)
    private String about;

    @Column(length = 1000)
    private String profilePic;

    private String phoneNumber;

    @Getter(AccessLevel.NONE)
    @Builder.Default
    private boolean enabled = true;

    // Example:
    // ROLE_EMPLOYEE
    // ROLE_ADMIN
    private String role;
    
    private String adharPdf;
    
    private String degreePdf;
    
    private String bankAccountPic;
    
    private String experienceLetterPdf;
    
    private String panPic;
    
    private LocalDate joiningDate;
    
    //cloudinary
    private String proCloudinaryId;
    private String adhCloudinaryId;
    private String degCloudinaryId;
    private String banCloudinaryId;
    private String expCloudinaryId;
    private String panCloudinaryId;
    
    private String mediaType;
    
    // Relation with SocialMediaUrls
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default 
    private List<SocialMediaUrls> socialMediaUrls=new ArrayList<>();

    
    
    @Override
    public Collection<? extends GrantedAuthority>
    getAuthorities() {

        return List.of(
                new SimpleGrantedAuthority(
                        role)
        );
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public String getUsername() {

        return this.email;
    }

    @Override
    public boolean isEnabled() {

        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }
}