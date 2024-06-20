package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.services.ContactServiceInterface;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	
	@Autowired
	private ContactServiceInterface contactServiceInterface;
	
	@Autowired
	private UserService userService;

	@GetMapping("/add")
	public String addContactView(Model model) {
		
		ContactForm contactForm = new ContactForm();
		
		model.addAttribute("contactForm", contactForm);
		return "user/add_contact";
	}
	
	@PostMapping("/processContacts")
	public String saveContact(@ModelAttribute ContactForm contactForm, Authentication authentication) {
		
		//fatching current user then fetch user id from current user it set 
		String userName = Helper.getEmailOfLoggedInUser(authentication);
		User user = userService.getUserByEmail(userName);
		
		
		  //converting ContactForm into contact
		  Contact contact = new Contact();
		  contact.setUser(user); contact.setName(contactForm.getName());
		  contact.setAddress(contactForm.getAddress());
		  contact.setDescription(contactForm.getDescription());
		  contact.setEmail(contactForm.getEmail());
		  contact.setFavorite(contactForm.isFavorite());
		  contact.setLinkdInLink(contactForm.getLinkdInLink());
		  contact.setPhoneNumber(contactForm.getPhoneNumber());
		  //contact.setPicture(contactForm.getPicture());
		  contact.setWebsiteLink(contactForm.getWebsiteLink());
		  
		  
		  
		// save data into database 
		 contactServiceInterface.saveContact(contact);
		 
		
		
		
		return "redirect:/user/contacts/add";
	}

}
