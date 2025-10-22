package com.uistify.backend.presentation.rest.controller;

import com.uistify.backend.domain.exception.FileStorageException;
import com.uistify.backend.domain.model.FileDownload;
import com.uistify.backend.domain.port.in.FileUseCase;
import com.uistify.backend.presentation.rest.dto.ErrorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Playlists", description = "CRUD playlists")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/file")
@Slf4j
public class FileController {

    @Autowired
    FileUseCase fileService;

    @Operation(summary = "Obtiene un archivo en bytes")
    @ApiResponse(responseCode = "200", description = "Acceso autorizado")
    @ApiResponse(responseCode = "403", description = "No autorizado")
    @GetMapping("/{key}")
    public ResponseEntity<Object> getFile(Authentication auth, @PathVariable("key") String key) {
        try {
            Optional<FileDownload> maybeFile = fileService.getFile(key);
            if (maybeFile.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ErrorDto.builder().status(404).message("Archivo no encontrado").build());
            }
            FileDownload file = maybeFile.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(ContentDisposition.attachment().filename(file.getFilename()).build());
            headers.setContentType(MediaType.parseMediaType(file.getContentType()));
            return new ResponseEntity<>(file.getContent(), headers, HttpStatus.OK);
        } catch (FileStorageException ex) {
            log.error("Error obteniendo archivo {}: {}", key, ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorDto.builder().status(500).message("Error obteniendo archivo").build());
        }
    }
}
