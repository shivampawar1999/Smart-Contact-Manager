package com.scm.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactServiceInterface;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

	// logger
	private Logger logger = LoggerFactory.getLogger(ContactController.class);

	@Autowired
	private ContactServiceInterface contactServiceInterface;

	@Autowired
	private UserService userService;

	@Autowired
	private ImageService imageService;

	@GetMapping("/add")
	public String addContactView(Model model) {

		ContactForm contactForm = new ContactForm();

		model.addAttribute("contactForm", contactForm);
		return "user/add_contact";
	}

	@PostMapping("/processContacts")
	public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult,
			Authentication authentication, HttpSession httpSession) {

		// fatching current user then fetch user id from current user it set
		String userName = Helper.getEmailOfLoggedInUser(authentication);
		User user = userService.getUserByEmail(userName);

		// validation
		if (bindingResult.hasErrors()) {
			httpSession.setAttribute("message",
					Message.builder().content("Please Correct the following errors").type(MessageType.red).build());
			return "user/add_contact";
		}

		// image processing
		logger.info("file information : {}", contactForm.getPicture().getOriginalFilename());

		String fileName = UUID.randomUUID().toString();

		String fileUrl = imageService.uploadImage(contactForm.getPicture(), fileName);

		// converting ContactForm into contact
		Contact contact = new Contact();
		contact.setUser(user);
		contact.setName(contactForm.getName());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setEmail(contactForm.getEmail());
		contact.setFavorite(contactForm.isFavorite());
		contact.setLinkdInLink(contactForm.getLinkdInLink());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setPicture(fileUrl);
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		contact.setCloudinaryImagePublicId(fileName);

		// save data into database
		contactServiceInterface.saveContact(contact);

		httpSession.setAttribute("message",
				Message.builder().content("New Contact Added Sucessfully").type(MessageType.green).build());
		return "redirect:/user/contacts/add";
	}

	// view contacts
	@GetMapping
	public String viewContacts(Authentication authentication, Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE +"") int size, @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		String loggedInUser = Helper.getEmailOfLoggedInUser(authentication);

		User user = userService.getUserByEmail(loggedInUser);

		// load all the contacts
		Page<Contact> pagecontacts = contactServiceInterface.getContactsByUser(user,page,size,sortBy,direction);

		model.addAttribute("pagecontacts", pagecontacts);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		model.addAttribute("contactSearchForm", new ContactSearchForm());
		

		return "user/contacts";
	}
	
	/* search */
	@GetMapping("/search")
	public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE +"") int size, @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction, Model model, Authentication authentication) {
		
		logger.info("field : {} and keyword : {}",contactSearchForm);
		
		//fetching current user email id
		User currentUser = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
		
		Page<Contact> pagecontacts = null;
				
		if(contactSearchForm.getField().equalsIgnoreCase("name")) {
			pagecontacts = contactServiceInterface.searchByName(contactSearchForm.getKeyword(), page, size, sortBy, direction, currentUser);
		}else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
			pagecontacts = contactServiceInterface.searchByEmail(contactSearchForm.getKeyword(), page, size, sortBy, direction, currentUser);
		}else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
			pagecontacts = contactServiceInterface.searchByPhone(contactSearchForm.getKeyword(), page, size, sortBy, direction, currentUser);
		} else {

		}
		
		logger.info("PageContact : {}",pagecontacts);
		model.addAttribute("pagecontacts", pagecontacts);
		model.addAttribute("contactSearchForm", contactSearchForm);
		model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
		
				
		return "user/search";
	}

}
