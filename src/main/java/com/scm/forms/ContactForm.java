package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {
	
	
	private String id;
	private String name;
	private String email;
	private String phoneNumber;
	private String address;
	private String description;
	private boolean favorite;
	private String websiteLink;
	private String linkdInLink;
	
	private MultipartFile picture;
	

}
