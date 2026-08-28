package com.springboot.scm.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.springboot.scm.entities.User;
import com.springboot.scm.repositories.UserRepo;

@Service
public class CustomOAuth2UserService
        implements OAuth2UserService
        <OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserRepo userRepo;

 @Override
public OAuth2User loadUser(
        OAuth2UserRequest request) {

    OAuth2User oauthUser =
            new DefaultOAuth2UserService()
                    .loadUser(request);

    String provider =
            request.getClientRegistration()
                    .getRegistrationId();

    String email = null;

    // GOOGLE
    if (provider.equalsIgnoreCase(
            "google")) {

        email =
                oauthUser.getAttribute(
                        "email");
    }

    // GITHUB
    else if (provider.equalsIgnoreCase(
            "github")) {

        email =
                oauthUser.getAttribute(
                        "email");

        // GitHub email may be null
        if (email == null) {

            String login =
                    oauthUser.getAttribute(
                            "login");

            email =
                    login
                    + "@gmail.com";
        }
    }

    User dbUser =
            userRepo.findByEmail(email)
                    .orElse(null);

    Set<GrantedAuthority>
            authorities =
            new HashSet<>(
                    oauthUser
                            .getAuthorities());

    if (dbUser != null) {

        dbUser.getAuthorities()
                .forEach(
                        authorities::add);
    }

    // Add email into attributes if missing
    Map<String, Object> attributes =
            new HashMap<>(
                    oauthUser.getAttributes());

    attributes.put(
            "email",
            email);

    // Always use email
    return new DefaultOAuth2User(
            authorities,
            attributes,
            "email");
}
}