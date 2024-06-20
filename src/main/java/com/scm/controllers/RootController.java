package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {
	

	//logger
	private Logger logger = LoggerFactory.getLogger(RootController.class);
	

	
	@Autowired
	private UserService userService;

	
	@ModelAttribute
	public void addLoggedInUserInformation(Model model, Authentication authentication) {
		
		if (authentication == null) {
			return;
		}
		
		System.out.println("adding Logged user information ... common method");
		
		String emailOfLoggedInUser = Helper.getEmailOfLoggedInUser(authentication);
		logger.info("User Logged in: {}", emailOfLoggedInUser);
		
	
		User user = userService.getUserByEmail(emailOfLoggedInUser);
		logger.info("User : {}"+user);
		
		model.addAttribute("loggedInUser",user);
		
	}
}
