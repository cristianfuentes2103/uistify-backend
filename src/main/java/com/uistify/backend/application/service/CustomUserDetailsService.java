package com.uistify.backend.application.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uistify.backend.infraestructure.persistence.jpa.Entity.UserEntity;
import com.uistify.backend.infraestructure.persistence.jpa.repository.UserJpaRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserJpaRepository userRepository;

	public CustomUserDetailsService(UserJpaRepository userRepository) {
		this.userRepository = userRepository;
	}

    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email)
				 .orElseThrow(() ->
						 new UsernameNotFoundException("User not found with email: "+ email));

		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(),
				Collections.emptyList());
        
	}
}
