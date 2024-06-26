package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactServiceInterface {

	Contact saveContact(Contact contact);
	Contact updateContact(Contact contact);
	List<Contact> getAllContacts();
	Contact getContactById(String id);
	void deleteContact(String id);
	
	//search
	List<Contact> searchBy(String name, String email, String phoneNumber);
	
	Page<Contact> searchByName(String name, int page, int size, String sortBy, String direction, User currentUser);
	
	Page<Contact> searchByEmail(String email, int page, int size, String sortBy, String direction, User currentUser);
	
	Page<Contact> searchByPhone(String phone, int page, int size, String sortBy, String direction, User currentUser);
	
	//getContactBy UserId
	List<Contact> getContactsByUserId(String UserId);
	
	//contact by user id
	Page<Contact> getContactsByUser(User user, int page, int size, String sortBy, String direction);
	
}
