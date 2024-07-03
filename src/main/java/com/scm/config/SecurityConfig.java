package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.scm.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private SecurityCustomUserDetailService userDetailService;
	
	@Autowired
	private OAuthAuthenticationSuccessHandler authAuthenticationSuccessHandler;
	
	@Autowired
	private AuthFailtureHandler authFailtureHandler;
	
	
	//configuration of authentication provider
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	
	//filterchain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	    httpSecurity.authorizeHttpRequests(authorize -> {
	        authorize.requestMatchers("/home", "/register", "/services").permitAll();
	        authorize.requestMatchers("/user/**").authenticated();
	        authorize.anyRequest().permitAll();
	    });
	    
	    httpSecurity.formLogin(formLogin -> {
	        formLogin.loginPage("/login");
	        formLogin.loginProcessingUrl("/authenticate");
	        formLogin.defaultSuccessUrl("/user/profile", true);
	        formLogin.failureUrl("/login?error=true");
	        formLogin.usernameParameter("email");
	        formLogin.passwordParameter("password");
	        
	        
	        //disable user failer handler
	        formLogin.failureHandler(authFailtureHandler);
	    });
	    
	    httpSecurity.csrf(AbstractHttpConfigurer :: disable);
	    httpSecurity.logout(logoutForm -> {
	    	logoutForm.logoutUrl("/logout");
	    	logoutForm.logoutSuccessUrl("/login?logout=true");
	    });
	    
	    //oauth configuration
	    httpSecurity.oauth2Login(oauth -> {
	    oauth.loginPage("/login");
	    oauth.successHandler(authAuthenticationSuccessHandler);
	    
	    });
	    
	    return httpSecurity.build();
	}
	
	

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}


}
