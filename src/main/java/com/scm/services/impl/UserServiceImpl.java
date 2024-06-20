package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public User saveUser(User user) {

		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);

		// password encoding
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// set the user role
		user.setRoleList(List.of(AppConstants.ROLE_USER));

		return userRepo.save(user);
	}

	@Override
	public Optional<User> getUserById(String id) {

		return userRepo.findById(id);
	}

	@Override
	public Optional<User> updateUser(User user) {
		User user1 = userRepo.findById(user.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

		user1.setName(user.getName());
		user1.setEmail(user.getEmail());
		user1.setPassword(user.getPassword());
		user1.setAbout(user.getAbout());
		user1.setPhoneNumber(user.getPhoneNumber());
		user1.setProfilePic(user.getProfilePic());
		user1.setEnabled(user.isEnabled());
		user1.setEmailVerified(user.isEmailVerified());
		user1.setPhoneVerified(user.isPhoneVerified());
		user1.setProvider(user.getProvider());
		user1.setProviderUserId(user.getProviderUserId());

		// save the user
		User save = userRepo.save(user1);

		return Optional.ofNullable(save);
	}

	@Override
	public void deleteUser(String id) {
		User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		userRepo.delete(user);
	}

	@Override
	public boolean isUserExist(String userId) {

		User user = userRepo.findById(userId).orElse(null);

		return user != null ? true : false;
	}

	@Override
	public boolean isUserExistByEmail(String email) {
		User user = userRepo.findByEmail(email).orElse(null);
		return user != null ? true : false;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	@Override
	public User getUserByEmail(String email) {

		return userRepo.findByEmail(email).orElse(null);
	}

}
