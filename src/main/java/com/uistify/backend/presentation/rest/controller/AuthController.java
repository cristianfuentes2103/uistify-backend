package com.uistify.backend.presentation.rest.controller;

import com.uistify.backend.domain.model.Otp;
import com.uistify.backend.domain.model.User;
import com.uistify.backend.domain.port.in.EmailUseCase;
import com.uistify.backend.domain.port.in.OtpUseCase;
import com.uistify.backend.domain.port.in.UserUseCase;
import com.uistify.backend.presentation.rest.dto.LoginDto;
import com.uistify.backend.presentation.rest.dto.SignUpDto;
import com.uistify.backend.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserUseCase userUseCase;
    private final EmailUseCase emailUseCase;
    private final OtpUseCase otpUseCase;

    private static final String HTML_BODY = "<!DOCTYPE html><html lang=\"es\"><head><meta charset=\"UTF-8\"><title>Bienvenido a Uistify</title><style>body{font-family:Arial,sans-serif;background-color:#f7f7f7;margin:0;padding:30px;color:#333}.container{background-color:#fff;max-width:600px;margin:auto;padding:25px;border-radius:10px}h1{text-align:center;color:#111;margin-bottom:10px}.greeting{font-size:16px;margin-bottom:20px;line-height:1.5}.verify-button{display:block;text-align:center;background-color:#4caf50;color:#fff;padding:12px 18px;margin:25px auto;width:fit-content;text-decoration:none;border-radius:6px;font-weight:700}.footer{margin-top:30px;font-size:12px;text-align:center;color:#777}</style></head><body><div class=\"container\"><h1>Uistify</h1><p class=\"greeting\">¡Hola <strong>%s</strong>!<br><br>¡Bienvenido a <strong>Uistify</strong>! Para continuar, por favor verifica tu correo haciendo clic en el siguiente enlace:</p><a class='verify-button' href='%s'>Verificar correo</a><div class=\"footer\">Si no solicitaste esta verificación, puedes ignorar este mensaje.</div></div></body></html>";

    @Operation(summary = "Inicia sesion para obtener un JWT")
    @ApiResponse(responseCode = "200", description = "Inicio de sesion exitoso")
    @ApiResponse(responseCode = "401", description = "Credenciales invalidas")
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        try {
            Optional<User> userOptional = userUseCase.findByEmail(loginDto.getEmail());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            User user = userOptional.get();
            if (!userUseCase.isPasswordValid(loginDto.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            if (user.getVerified() == 0) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("{\"token\":\"" + JwtUtil.generateToken(loginDto.getEmail()) + "\"}");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/verify", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> verifyCode(@RequestParam("code") String code) {
        Otp otp = otpUseCase.findByCode(code);
        if (otpUseCase.isOtpValid(otp)) {
            User user = userUseCase.findById(otp.getUserId());

            User userUpdated = user.toBuilder()
                    .verified(1)
                    .build();

            String html = """
                        <!DOCTYPE html>
                        <html lang="es">
                        <head>
                            <meta charset="UTF-8">
                            <title>Correo Verificado</title>
                            <style>
                                body { 
                                    font-family: Arial, sans-serif; 
                                    background: #f5f5f5; 
                                    text-align: center; 
                                    padding-top: 60px;
                                }
                                .box {
                                    background: white;
                                    padding: 30px;
                                    margin: auto;
                                    width: 400px;
                                    border-radius: 10px;
                                    box-shadow: 0 0 10px rgba(0,0,0,0.1);
                                }
                                h1 { color: #4CAF50; }
                            </style>
                        </head>
                        <body>
                            <div class="box">
                                <h1>✔ Correo Verificado</h1>
                                <p>Tu correo ha sido verificado correctamente.</p>
                            </div>
                        </body>
                        </html>
                    """;

            userUseCase.update(userUpdated);

            return ResponseEntity.ok(html);
        } else {
            String errorHtml = """
                        <!DOCTYPE html>
                        <html lang="es">
                        <head>
                            <meta charset="UTF-8">
                            <title>Código inválido</title>
                        </head>
                        <body style="text-align:center; padding-top:60px; font-family:Arial;">
                            <h1 style="color:red;">✖ Código inválido</h1>
                            <p>No pudimos verificar tu correo.</p>
                        </body>
                        </html>
                    """;
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorHtml);
        }
    }

    @Operation(summary = "Registrar un usuario nuevo")
    @ApiResponse(responseCode = "200", description = "Usuario creado con exito")
    @ApiResponse(responseCode = "409", description = "Email ya existente")
    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto, HttpServletRequest request) {
        try {
            if (userUseCase.existsByEmail(signUpDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already taken");
            }

            User newUser = User.builder()
                    .name(signUpDto.getName())
                    .email(signUpDto.getEmail())
                    .password(signUpDto.getPassword())
                    .verified(0)
                    .build();

            User created = userUseCase.register(newUser);

            Otp otp = otpUseCase.saveOtp(created.getId());

            String host = request.getHeader("Host");
            String scheme = request.getHeader("X-Forwarded-Proto");

            String verificationLink = String.format("%s://%s/api/auth/verify?code=%s",
                    scheme,
                    host,
                    otp.getCode()
            );

            emailUseCase.sendEmailHtml(signUpDto.getEmail(), "Verifica tu correo", String.format(HTML_BODY,
                    signUpDto.getName(), verificationLink));

            return ResponseEntity.ok("{\"token\":\"" + JwtUtil.generateToken(created.getEmail()) + "\"}");

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
