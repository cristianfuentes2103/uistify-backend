package com.uistify.backend.presentation.controller;

import com.uistify.backend.persistence.model.User;
import com.uistify.backend.persistence.repository.UserRepository;
import com.uistify.backend.presentation.dto.LoginDto;
import com.uistify.backend.presentation.dto.SignUpDto;
import com.uistify.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>(JwtUtil.generateToken(loginDto.getEmail()), HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto){
		if (userRepository.existsByEmail(signUpDto.getEmail())){
			return new ResponseEntity<>("Email already taken", HttpStatus.CONFLICT);
		}

		User user = new User();
		user.setName(signUpDto.getName());
		user.setEmail(signUpDto.getEmail());
		user.setPassword( passwordEncoder.encode(signUpDto.getPassword()) );

		userRepository.save(user);

		return new ResponseEntity<>(JwtUtil.generateToken(user.getEmail()), HttpStatus.OK);
	}
}
