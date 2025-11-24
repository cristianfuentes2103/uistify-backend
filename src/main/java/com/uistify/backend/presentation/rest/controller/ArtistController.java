package com.uistify.backend.presentation.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uistify.backend.application.mapper.ArtistMapper;
import com.uistify.backend.application.mapper.SongMapper;
import com.uistify.backend.domain.model.Artist;
import com.uistify.backend.domain.model.Song;
import com.uistify.backend.domain.port.in.ArtistUseCase;
import com.uistify.backend.domain.port.out.ArtistRepository;
import com.uistify.backend.presentation.rest.dto.ArtistDto;
import com.uistify.backend.presentation.rest.dto.SongDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Módulo artistas", description = "Gestión del artista propio del usuario")
@RestController
@RequestMapping("/api/artist")
@RequiredArgsConstructor
@Slf4j
public class ArtistController {

	private final ArtistUseCase artistUseCase;

	private static final ArtistMapper ARTIST_MAPPER = ArtistMapper.INSTANCE;
	private static final SongMapper SONG_MAPPER = SongMapper.INSTANCE;

	private final ArtistRepository artistRepository;

	@Operation(summary = "Obtener artista del usuario")
	@ApiResponse(responseCode = "200", description = "Artista correcto")
	@ApiResponse(responseCode = "400", description = "El usuario aun no crea su artista")
	@GetMapping
	public ResponseEntity<ArtistDto> getOwnArtist(Authentication auth){
		try {
			Artist artist = artistUseCase.getOwnArtist(auth.getName())
				.orElseThrow(() -> new IllegalArgumentException("Artist not found"));
			return ResponseEntity.ok(ARTIST_MAPPER.toDto(artist)) ;
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
		}
	}

	@Operation(summary = "Crear artista de usuario")
	@ApiResponse(responseCode = "200", description = "Artista creado")
	@ApiResponse(responseCode = "400", description = "Ya existe el artista")
	@PostMapping
	public ResponseEntity<ArtistDto> createOwnArtist(Authentication auth, @RequestBody ArtistDto dto){
		try {
			Artist newArtist = artistUseCase.createOwnArtist(auth.getName(), ARTIST_MAPPER.toDomain(dto));
			return ResponseEntity.ok(ARTIST_MAPPER.toDto(newArtist));
		} catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
		}
	}

	@Operation(summary = "Actualizar artista de usuario")
	@ApiResponse(responseCode = "200", description = "Artista actualizado")
	@ApiResponse(responseCode = "400", description = "No existe el artista")
	@PutMapping
	public ResponseEntity<ArtistDto> updateOwnArtist(Authentication auth, @RequestBody ArtistDto dto){
		try {
			Artist updatedArtist = artistUseCase.updateOwnArtist(auth.getName(), ARTIST_MAPPER.toDomain(dto));
			return ResponseEntity.ok(ARTIST_MAPPER.toDto(updatedArtist));
		} catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
		}
	}

	@Operation(summary = "Subir detalle de canción")
	@ApiResponse(responseCode = "200", description = "Canción subida")
	@ApiResponse(responseCode = "400", description = "No existe el artista")
	@PostMapping("/songs")
	public ResponseEntity<SongDto> uploadSongDetail(Authentication auth, @RequestBody SongDto dto){
		try {
			Song uploadedSong = artistUseCase.uploadSongDetail(auth.getName(), SONG_MAPPER.toDomain(dto));
			SongDto finalSong = SONG_MAPPER.toDto(uploadedSong);
			finalSong.setArtist(artistRepository.findById(uploadedSong.getArtistId()).get().getName());

			return ResponseEntity.ok(finalSong);
		} catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
		}
	}
}
