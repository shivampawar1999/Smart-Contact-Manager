package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
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
	@NotBlank(message = "Name is required")
	private String name;
	
	@Email(message = "Invalid Email address")
	private String email;
	
	@NotBlank(message = "phone number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone number")
	private String phoneNumber;
	
	@NotBlank(message = "Address is required")
	private String address;
	private String description;
	private boolean favorite;
	private String websiteLink;
	private String linkdInLink;
	
	private MultipartFile picture;
	

}
