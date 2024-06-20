package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

	@Id
	private String userId;
	@Column(name = "user_name", nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
	private String email;
	@Getter(value = AccessLevel.NONE)
	private String password;
	@Column(length = 6000)
	private String about;
	@Column(length = 6000)
	private String profilePic;
	private String phoneNumber;

	/* information */
	@Getter(value = AccessLevel.NONE)
	private boolean enabled = true;
	private boolean emailVerified = false;
	private boolean phoneVerified = false;

	/* provider = SELF, GOOGLE, GITHUB */
	@Enumerated(value = EnumType.STRING)
	private Providers provider = Providers.SELF;
	private String providerUserId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Contact> contacts = new ArrayList<>();

	/*
	 * ------------------ User detailUser implemented methods
	 * ---------------------------------------
	 */

	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roleList = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		return roles;
	}

	@Override
	public String getUsername() {

		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enabled;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

}
