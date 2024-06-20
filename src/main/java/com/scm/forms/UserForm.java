package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserForm {

	@NotBlank(message = "UserName is Required !!")
	@Size(min = 3, message = "Minimun 3 character required !!")
	private String name;

	@NotBlank(message = "Email is Required !!")
	@Email(message = "Invalid Email Address")
	private String email;

	@NotBlank(message = "Password is required !!")
	@Size(min = 6, message = "minimum 6 character required !!")
	private String password;

	@NotBlank(message = "About is required !!")
	private String about;

	@Size(min = 6, max = 12, message = "Invalid Phone Number !!")
	private String phoneNumber;
}
