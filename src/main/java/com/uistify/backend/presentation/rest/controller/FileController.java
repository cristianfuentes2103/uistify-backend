package com.uistify.backend.presentation.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uistify.backend.domain.port.in.FileUseCase;

@Tag(name = "Playlists", description = "CRUD playlists")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    FileUseCase fileService;

    @Operation(summary = "Obtiene un archivo en bytes")
    @ApiResponse(responseCode = "200", description = "Acceso autorizado")
    @ApiResponse(responseCode = "403", description = "No autorizado")
    @GetMapping("/{key}")
    public ResponseEntity<Object> getFile(Authentication auth, @PathVariable("key") String key) {
        return fileService.getFile(key);
    }

}
