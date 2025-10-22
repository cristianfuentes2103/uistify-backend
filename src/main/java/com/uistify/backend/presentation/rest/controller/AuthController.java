package com.uistify.backend.presentation.rest.controller;

import com.uistify.backend.domain.model.User;
import com.uistify.backend.domain.port.in.UserUseCase;
import com.uistify.backend.presentation.rest.dto.LoginDto;
import com.uistify.backend.presentation.rest.dto.SignUpDto;
import com.uistify.backend.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Tag(name = "Autenticacion", description = "Gestion de sesion y tokens JWT")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserUseCase userUseCase;

    @Operation(summary = "Inicia sesion para obtener un JWT")
    @ApiResponse(responseCode = "200", description = "Inicio de sesion exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales invalidas")
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Optional<User> userOptional = userUseCase.findByEmail(loginDto.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        User user = userOptional.get();
        if (!userUseCase.isPasswordValid(loginDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("{\"token\":\"" + JwtUtil.generateToken(loginDto.getEmail()) + "\"}");
    }

    @Operation(summary = "Registrar un usuario nuevo")
    @ApiResponse(responseCode = "200", description = "Usuario creado con exito")
    @ApiResponse(responseCode = "409", description = "Email ya existente")
    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {
        if (userUseCase.existsByEmail(signUpDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already taken");
        }

        User newUser = User.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .build();

        User created = userUseCase.register(newUser);

        return ResponseEntity.ok("{\"token\":\"" + JwtUtil.generateToken(created.getEmail()) + "\"}");
    }
}
