package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.Contact;
import com.scm.services.ContactServiceInterface;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private ContactServiceInterface contactServiceInterface;
	
	// logger
	private Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	//get contact
	@GetMapping("/contacts/{contactId}")
	public Contact getContact(@PathVariable String contactId) {
		
		return contactServiceInterface.getContactById(contactId);
	}

}
