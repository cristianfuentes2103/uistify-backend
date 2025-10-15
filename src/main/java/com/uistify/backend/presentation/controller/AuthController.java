package com.uistify.backend.presentation.controller;

import com.uistify.backend.persistence.model.User;
import com.uistify.backend.persistence.repository.UserRepository;
import com.uistify.backend.presentation.dto.LoginDto;
import com.uistify.backend.presentation.dto.SignUpDto;
import com.uistify.backend.util.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

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

@SecurityScheme(
  name = "bearerAuth",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT"
)
@Tag(name = "Autenticación", description = "Gestión de sesión y tokens JWT")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Operation(summary = "Inicia sesión para obtener un JWT")
	@ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso")
	@ApiResponse(responseCode = "401", description = "Credenciales inválidas")
	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
		if (!userRepository.existsByEmail(loginDto.getEmail())){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (!userRepository.findByEmail(loginDto.getEmail()).
			map(u -> passwordEncoder.matches(loginDto.getPassword(), u.getPassword())).
			orElse(false)){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>("{\"token\":\""+JwtUtil.generateToken(loginDto.getEmail())+"\"}", HttpStatus.OK);
	}

	@Operation(summary = "Registrar un usuario nuevo")
	@ApiResponse(responseCode = "200", description = "Usuario creado con éxito")
	@ApiResponse(responseCode = "409", description = "Email ya existente")
	@PostMapping("/register")
	public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto){
		if (userRepository.existsByEmail(signUpDto.getEmail())){
			return new ResponseEntity<>("Email already taken", HttpStatus.CONFLICT);
		}

		User user = new User();
		user.setName(signUpDto.getName());
		user.setEmail(signUpDto.getEmail());
		user.setPassword( passwordEncoder.encode(signUpDto.getPassword()) );

		userRepository.save(user);

		return new ResponseEntity<>("{\"token\":\""+JwtUtil.generateToken(user.getEmail())+"\"}", HttpStatus.OK);
	}
}
