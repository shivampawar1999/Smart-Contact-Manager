package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactServiceInterface;

@Service
public class contactServiceImpl implements ContactServiceInterface {
	
	@Autowired
	private ContactRepo contactRepo;

	@Override
	public Contact saveContact(Contact contact) {
		
		String contactId = UUID.randomUUID().toString();
		contact.setId(contactId);
		
		return contactRepo.save(contact);
	}

	@Override
	public Contact updateContact(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getAllContacts() {
		
		return contactRepo.findAll();
	}

	@Override
	public Contact getContactById(String id) {
		return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given id "+ id));
	}

	@Override
	public void deleteContact(String id) {
		Contact contact = contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact not found with given id "+ id));
		contactRepo.delete(contact);
	}

	@Override
	public List<Contact> searchBy(String name, String email, String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getContactsByUserId(String UserId) {
		
		return contactRepo.findByUserId(UserId);
	}

	@Override
	public Page<Contact> getContactsByUser(User user, int page, int size, String sortBy, String direction) {
		
		
		Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		PageRequest pageable = PageRequest.of(page, size);
		
		return contactRepo.findByUser(user, pageable);
	}

	

	

}
