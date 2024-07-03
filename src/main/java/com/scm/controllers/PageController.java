package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("name", "shivam pawar");
		return "home";
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("name", "shivam pawar");
		return "about";
	}

	@GetMapping("/services")
	public String services(Model model) {
		model.addAttribute("name", "shivam pawar");
		return "services";
	}

	@GetMapping("/contact")
	public String contact(Model model) {
		model.addAttribute("name", "shivam pawar");
		return "contact";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("name", "shivam pawar");
		return "login";
	}

	@GetMapping("/register")
	public String signup(Model model) {

		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);

		return "register";
	}

	// process user register from
	@PostMapping("/doRegister")
	public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult,
			HttpSession session) {

		// fetch from data

		// validate form data
		if (bindingResult.hasErrors()) {
			return "register";
		}

		// save data to database
		User user = new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setAbout(userForm.getAbout());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setEnabled(false);
		user.setProfilePic(
				"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2Fvibrant-tiktok-default-profile-picture-sticker--679902875018963865%2F&psig=AOvVaw1A8jz3ZPbKAc-13TtKOs-W&ust=1717487002561000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCLiO5-n3voYDFQAAAAAdAAAAABAE");

		User saveUser = userService.saveUser(user);

		// message = "register successful"
		Message message = Message.builder().content("Registration SucessFully").type(MessageType.green).build();
		session.setAttribute("message", message);
		// redirect to login page

		return "redirect:/register";
	}

}
