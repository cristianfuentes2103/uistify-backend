package com.uistify.backend.presentation.rest.controller;

import com.uistify.backend.application.mapper.SongMapper;
import com.uistify.backend.domain.port.in.SongUseCase;
import com.uistify.backend.presentation.rest.dto.SongDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Canciones", description = "Catalogo publico de canciones")
@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
@Slf4j
public class SongController {

    private static final SongMapper SONG_MAPPER = SongMapper.INSTANCE;

    private final SongUseCase songUseCase;

    @Operation(summary = "Retorna una lista con las canciones existentes en la base de datos.",
            description = "Se obtiene un fragmento del total de canciones al especificar los parametros `page` y `size` de la siguiente forma `/api/songs?page=<Numero pagina>&size=<Tamano pagina>`")
    @ApiResponse(responseCode = "200", description = "Catalogo de canciones.")
    @GetMapping
    public ResponseEntity<List<SongDto>> getAllSongs(Pageable pageable) {
        try {
            List<SongDto> songs = songUseCase.getAllSongs(pageable).stream()
                    .map(SONG_MAPPER::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(songs);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Retorna la cancion con el ID.")
    @ApiResponse(responseCode = "200", description = "Cancion encontrada.")
    @ApiResponse(responseCode = "404", description = "No existe la cancion con ese ID.")
    @GetMapping("/{songId}")
    public ResponseEntity<SongDto> getSongById(@PathVariable Long songId) {
        try {
            return songUseCase.getSongById(songId)
                    .map(SONG_MAPPER::toDto)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
