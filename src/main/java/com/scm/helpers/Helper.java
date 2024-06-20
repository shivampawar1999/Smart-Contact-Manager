package com.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.experimental.var;

public class Helper {

	public static String getEmailOfLoggedInUser(Authentication authentication) {

		if (authentication instanceof OAuth2AuthenticationToken) {

			OAuth2AuthenticationToken oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
			String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

			OAuth2User auth2User = (OAuth2User) authentication.getPrincipal();
			String userName = Helper.getEmailOfLoggedInUser(authentication);

			// SELF Login

			if (authorizedClientRegistrationId.equalsIgnoreCase("google")) {

				// Google Login
				System.out.println("Geting Email from google");
				userName = auth2User.getAttribute("email");

			} else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
				// Google Login
				System.out.println("Geting Email from github");
				userName = auth2User.getAttribute("email") != null ? auth2User.getAttribute("email").toString()
						: auth2User.getAttribute("login").toString() + "gmail.com";
			}

			return userName;

			/*-------------------------------------*/

		} else {
			System.out.println("Getting data from local database");
			return authentication.getName();
		}

	}
}
