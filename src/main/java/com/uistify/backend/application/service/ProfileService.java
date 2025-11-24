package com.uistify.backend.application.service;

import org.springframework.stereotype.Service;

import com.uistify.backend.domain.model.User;
import com.uistify.backend.domain.port.in.ProfileUseCase;
import com.uistify.backend.domain.port.out.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService implements ProfileUseCase{

	private final UserRepository userRepository;

	@Override
	public User getProfile(String email){
		return userRepository.findByEmail(email).orElse(null);
	}
}
