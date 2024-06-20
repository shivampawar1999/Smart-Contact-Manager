package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//logger
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	

	
	@Autowired
	private UserService userService;

	@GetMapping("/dashboard")
	public String userDashboard() {

		return "user/dashboard";
	}

	@GetMapping("/profile")
	public String userProfile() {		
		
		return "user/profile";
	}

}
