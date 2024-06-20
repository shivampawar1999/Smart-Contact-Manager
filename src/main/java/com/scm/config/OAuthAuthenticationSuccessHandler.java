package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.var;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

	@Autowired
	private UserRepo userRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub

		logger.info("OAuthAuthenticationSuccessHandler");

		// we have to identify the provider

		OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

		String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

		logger.info("Provider is => " + authorizedClientRegistrationId);

		DefaultOAuth2User auth2User = (DefaultOAuth2User) authentication.getPrincipal();

		auth2User.getAttributes().forEach((key, value) -> {
			logger.info("{} => {}", key, value);

		});

		// creating User
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setRoleList(List.of(AppConstants.ROLE_USER));
		user.setEmailVerified(true);
		user.setEnabled(true);
		user.setPassword("dummy");

		if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

			// google code
			user.setEmail(auth2User.getAttribute("email").toString());
			user.setProfilePic(auth2User.getAttribute("picture").toString());
			user.setName(auth2User.getAttribute("name").toString());
			user.setProviderUserId(auth2User.getName());
			user.setProvider(Providers.GOOGLE);
			user.setAbout("This accout created using GOOGLE Login");

		} else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
			// github code

			String email = auth2User.getAttribute("email") != null ? auth2User.getAttribute("email").toString()
					: auth2User.getAttribute("login").toString() + "gmail.com";

			String picture = auth2User.getAttribute("avatar_url").toString();
			String name = auth2User.getAttribute("login").toString();
			String providerUserId = auth2User.getName();

			user.setEmail(email);
			user.setProfilePic(picture);
			user.setName(name);
			user.setProviderUserId(providerUserId);
			user.setProvider(Providers.GITHUB);
			user.setAbout("This Account is created Using GITHUB Login");
		} else {
			logger.info("Unkonwn Provider");
		}

		// check user already exist or not
		User existingUser = userRepo.findByEmail(user.getEmail()).orElse(null);
		if (existingUser == null) {
			userRepo.save(user);

		}

		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

	}

}
