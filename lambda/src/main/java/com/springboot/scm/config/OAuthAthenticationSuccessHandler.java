package com.springboot.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.springboot.scm.entities.Providers;
import com.springboot.scm.entities.User;
import com.springboot.scm.helpers.AppConstants;
import com.springboot.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    Logger logger =
            LoggerFactory.getLogger(
                    OAuthAthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        logger.info(
                "OAuthAthenticationSuccessHandler");

        // Provider check
        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) authentication;

        String provider =
                oauthToken
                        .getAuthorizedClientRegistrationId();

        // Get OAuth user details
        DefaultOAuth2User oauthUser =
                (DefaultOAuth2User)
                        authentication.getPrincipal();

        oauthUser.getAttributes()
                .forEach((key, value) -> {
                    logger.info(
                            key + " : " + value);
                });
        //checking role
        authentication.getAuthorities()
        .forEach(auth ->
                System.out.println(
                        "Authority = "
                        + auth.getAuthority()));

        User user = new User();

        user.setUserId(
                UUID.randomUUID().toString());

        user.setRoleList(
                List.of(AppConstants.ROLE_USER));

        user.setEmailVerified(true);

        user.setEnabled(true);

        // encoded dummy password
        user.setPassword(
                passwordEncoder.encode(
                        "oauth_user"));

        // ================= GOOGLE LOGIN =================
     // ================= GOOGLE LOGIN =================
        if (provider.equalsIgnoreCase("google")) {

            user.setEmail(
                    oauthUser.getAttribute("email"));

            user.setProfilePic(
                    oauthUser.getAttribute("picture"));

            user.setName(
                    oauthUser.getAttribute("name"));

            // Google unique ID
            user.setProviderUserId(
                    oauthUser.getAttribute("sub"));

            user.setProvider(
                    Providers.GOOGLE);

            user.setAbout(
                    "Login with Google");
        }

        // ================= GITHUB LOGIN =================
        else if (provider.equalsIgnoreCase("github")) {

            // Safe login fetch
            String login = oauthUser.getAttribute("login") != null
                    ? oauthUser.getAttribute("login").toString()
                    : "github_user";

            // Email may be null in GitHub
            String email = oauthUser.getAttribute("email") != null
                    ? oauthUser.getAttribute("email").toString()
                    : login + "@github.local";

            // Profile picture
            String picture = oauthUser.getAttribute("avatar_url") != null
                    ? oauthUser.getAttribute("avatar_url").toString()
                    : "";

            // Name may be null
            String name = oauthUser.getAttribute("name") != null
                    ? oauthUser.getAttribute("name").toString()
                    : login;

            user.setEmail(email);
            user.setName(name);
            user.setProfilePic(picture);
            user.setProvider(Providers.GITHUB);

            // Never keep provider user id null
            user.setProviderUserId(login);

            user.setAbout("Login with GitHub");

            logger.info("GitHub Login Success");
            logger.info("Login: {}", login);
            logger.info("Email: {}", email);
        }
       
        else {
            logger.info(
                    "Unknown OAuth Provider");
        }

        // Check if user already exists
        User existingUser =
                userRepo.findByEmail(
                        user.getEmail())
                        .orElse(null);

        // Save only if new user
        if (existingUser == null) {

            userRepo.save(user);

            logger.info(
                    "New OAuth user saved");
        } else {

            logger.info(
                    "User already exists");
        }

        // Redirect after login
        new DefaultRedirectStrategy()
                .sendRedirect(
                        request,
                        response,
                        "/user/profile");
    }
}