package com.scm.services;

import java.util.List;

import com.scm.entities.Contact;

public interface ContactServiceInterface {

	Contact saveContact(Contact contact);
	Contact updateContact(Contact contact);
	List<Contact> getAllContacts();
	Contact getContactById(String id);
	void deleteContact(String id);
	
	//search
	List<Contact> searchBy(String name, String email, String phoneNumber);
	
	//getContactBy UserId
	List<Contact> getContactsByUserId(String UserId);
	
}
