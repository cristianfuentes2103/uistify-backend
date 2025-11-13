package com.uistify.backend.presentation.rest.controller;

import com.uistify.backend.application.mapper.PlaylistMapper;
import com.uistify.backend.domain.model.Playlist;
import com.uistify.backend.domain.port.in.PlaylistUseCase;
import com.uistify.backend.presentation.rest.dto.PlaylistDetailDto;
import com.uistify.backend.presentation.rest.dto.PlaylistDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Playlists", description = "CRUD playlists")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
@Slf4j
public class PlaylistController {

    private static final PlaylistMapper PLAYLIST_MAPPER = PlaylistMapper.INSTANCE;

    private final PlaylistUseCase playlistUseCase;

    @Operation(summary = "Obtiene todas las playlists del usuario")
    @ApiResponse(responseCode = "200", description = "Acceso autorizado")
    @ApiResponse(responseCode = "403", description = "No autorizado")
    @GetMapping
    public ResponseEntity<List<PlaylistDto>> getAllPlaylist(Authentication auth) {
        try {
            List<PlaylistDto> playlists = playlistUseCase.getAllPlaylistsByUserEmail(auth.getName()).stream()
                    .map(PLAYLIST_MAPPER::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(playlists);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Crea una nueva playlist.")
    @ApiResponse(responseCode = "200", description = "Acceso autorizado")
    @ApiResponse(responseCode = "403", description = "No autorizado")
    @PostMapping
    public ResponseEntity<PlaylistDto> createPlaylist(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Playlist nueva", required = true)
            @RequestBody PlaylistDto playlistDto,
            Authentication auth) {
        try {
            Playlist created = playlistUseCase.createPlaylist(auth.getName(), PLAYLIST_MAPPER.toDomain(playlistDto));
            return ResponseEntity.ok(PLAYLIST_MAPPER.toDto(created));
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Actualiza una playlist.")
    @ApiResponse(responseCode = "200", description = "Playlist actualizada")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @PutMapping
    public ResponseEntity<PlaylistDto> updatePlaylist(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Playlist actualizada, con un id existente", required = true)
            @RequestBody PlaylistDto playlistDtoUpdate,
            Authentication auth) {
        try {
            Playlist updated = playlistUseCase.updatePlaylist(auth.getName(), PLAYLIST_MAPPER.toDomain(playlistDtoUpdate));
            return ResponseEntity.ok(PLAYLIST_MAPPER.toDto(updated));
        } catch (SecurityException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Devuelve la playlist con sus canciones.")
    @ApiResponse(responseCode = "200", description = "Playlist proporcionada")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistDetailDto> getPlaylist(
            @Parameter(description = "id de playlist")
            @PathVariable Long playlistId,
            Authentication auth) {
        try {
            Playlist playlist = playlistUseCase.getPlaylistDetail(auth.getName(), playlistId);
            return ResponseEntity.ok(PLAYLIST_MAPPER.toDetailDto(playlist));
        } catch (SecurityException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Elimina una playlist.")
    @ApiResponse(responseCode = "204", description = "Playlist eliminada con exito.")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(
            @Parameter(description = "id de playlist")
            @PathVariable Long playlistId,
            Authentication auth) {
        try {
            playlistUseCase.deletePlaylist(auth.getName(), playlistId);
            return ResponseEntity.noContent().build();
        } catch (SecurityException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Anade una cancion a una playlist.")
    @ApiResponse(responseCode = "201", description = "Cancion anadida a la playlist con exito.")
    @ApiResponse(responseCode = "404", description = "Cancion no encontrada.")
    @ApiResponse(responseCode = "400", description = "Cancion ya anadida en la lista.")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> addSongToPlaylist(
            @Parameter(description = "id de playlist")
            @PathVariable Long playlistId,
            @Parameter(description = "id de cancion")
            @PathVariable Long songId,
            Authentication auth) {
        try {
            playlistUseCase.addSongToPlaylist(auth.getName(), playlistId, songId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (SecurityException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalStateException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Elimina una cancion de una playlist.")
    @ApiResponse(responseCode = "204", description = "Cancion eliminada con exito.")
    @ApiResponse(responseCode = "404", description = "No existe la cancion en la playlist.")
    @ApiResponse(responseCode = "401", description = "No autorizado")
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> deleteSongFromPlaylist(
            @Parameter(description = "id de playlist")
            @PathVariable Long playlistId,
            @Parameter(description = "id de cancion")
            @PathVariable Long songId,
            Authentication auth) {
        try {
            playlistUseCase.deleteSongFromPlaylist(auth.getName(), playlistId, songId);
            return ResponseEntity.noContent().build();
        } catch (SecurityException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
