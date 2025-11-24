package com.uistify.backend.presentation.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uistify.backend.application.mapper.PlaylistMapper;
import com.uistify.backend.application.mapper.SongMapper;
import com.uistify.backend.domain.model.Playlist;
import com.uistify.backend.domain.model.Song;
import com.uistify.backend.domain.port.in.PlaylistUseCase;
import com.uistify.backend.domain.port.out.ArtistRepository;
import com.uistify.backend.presentation.rest.dto.PlaylistDto;
import com.uistify.backend.presentation.rest.dto.SongDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Playlist públicas", description = "Acceso a playlists públicas y sus canciones.")
@RestController
@RequestMapping("/api/public-playlists")
@RequiredArgsConstructor
@Slf4j
public class PublicPlaylistController {

	private static final PlaylistMapper PLAYLIST_MAPPER = PlaylistMapper.INSTANCE;
	private static final SongMapper SONG_MAPPER = SongMapper.INSTANCE;

	private final PlaylistUseCase playlistUseCase;

	private final ArtistRepository artistRepository;

	@Operation(summary = "Obtiene todas las playlist públicas")
	@ApiResponse(responseCode = "200", description = "Correcto")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@GetMapping
	public ResponseEntity<List<PlaylistDto>> getAllPublicPlaylists(){
		try {
			List<PlaylistDto> playlists = playlistUseCase.getAllPublicPlaylists().stream()
				.map(PLAYLIST_MAPPER::toDto)
				.collect(Collectors.toList());
			return ResponseEntity.ok(playlists);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@Operation(summary = "Retorna el detalle de la playlist pública.")
	@ApiResponse(responseCode = "200", description = "Acceso correcto")
	@ApiResponse(responseCode = "404", description = "No se encontró una playlist con esa ID")
	@ApiResponse(responseCode = "401", description = "La playlist no es pública")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@GetMapping("/{playlistId}")
	public ResponseEntity<PlaylistDto> getPublicPlaylist(@PathVariable Long playlistId){
		try {
			Playlist playlist = playlistUseCase.getPublicPlaylist(playlistId);
			return ResponseEntity.ok(PLAYLIST_MAPPER.toDto(playlist));
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

	@Operation(summary = "Retorna las canciones de la playlist pública.")
	@ApiResponse(responseCode = "200", description = "Acceso correcto")
	@ApiResponse(responseCode = "404", description = "No se encontró una playlist con esa ID")
	@ApiResponse(responseCode = "401", description = "La playlist no es pública")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@GetMapping("/{playlistId}/songs")
	public ResponseEntity<List<SongDto>> getSongsFromPlaylist(@PathVariable Long playlistId){
		try {
			List<Song> songs = playlistUseCase.getSongsFromPublicPlaylist(playlistId);
            return ResponseEntity.ok(songs.stream().map(song -> {
					SongDto dto = SONG_MAPPER.toDto(song);
					String artistName = artistRepository.findById(song.getArtistId()).get().getName();
					dto.setArtist(artistName);
					return dto;
				}).collect(Collectors.toList()));
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
