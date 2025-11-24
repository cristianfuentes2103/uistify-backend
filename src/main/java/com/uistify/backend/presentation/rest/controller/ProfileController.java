package com.uistify.backend.presentation.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.uistify.backend.application.mapper.ProfileMapper;
import com.uistify.backend.domain.port.in.ProfileUseCase;
import com.uistify.backend.presentation.rest.dto.ProfileDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Perfil del usuario", description = "Muestra información del perfil de usuario")
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

	private static final ProfileMapper PROFILE_MAPPER = ProfileMapper.INSTANCE;
	private final ProfileUseCase profileUseCase;

	@Operation(summary = "Muestra información relacionada al usuario logueado.")
	@GetMapping
	public ProfileDto getProfile(Authentication auth){
		return PROFILE_MAPPER.toDto( profileUseCase.getProfile(auth.getName()));
	}
}
