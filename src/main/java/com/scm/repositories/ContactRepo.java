package com.scm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
	
	//find by user
	List<Contact> findByUser(User user);
	
	//find by Userid
	@Query("SELECT c FROM Contact c WHERE c.user.id =: userId ")
	List<Contact> findByUserId(@Param("userId") String userId);
	

}
