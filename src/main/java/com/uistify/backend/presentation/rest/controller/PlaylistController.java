package com.uistify.backend.presentation.rest.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uistify.backend.application.mapper.PlaylistMapper;
import com.uistify.backend.application.service.PlaylistService;
import com.uistify.backend.infraestructure.persistence.jpa.Entity.PlaylistEntity;
import com.uistify.backend.infraestructure.persistence.jpa.Entity.UserEntity;
import com.uistify.backend.infraestructure.persistence.jpa.repository.PlaylistJpaRepository;
import com.uistify.backend.infraestructure.persistence.jpa.repository.SongJpaRepository;
import com.uistify.backend.infraestructure.persistence.jpa.repository.UserJpaRepository;
import com.uistify.backend.presentation.rest.dto.PlaylistDetailDto;
import com.uistify.backend.presentation.rest.dto.PlaylistDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Playlists", description = "CRUD playlists")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

	@Autowired
	PlaylistService playlistService;

	@Autowired
	UserJpaRepository userRepository;

	@Autowired
	PlaylistJpaRepository playlistRepository;

	@Autowired
	SongJpaRepository songRepository;

	@Operation(summary = "Obtiene todas las playlists del usuario")
	@ApiResponse(responseCode = "200", description = "Acceso autorizado")
	@ApiResponse(responseCode = "403", description = "No autorizado")
	@GetMapping
	public ResponseEntity<List<PlaylistDto>> getAllPlaylist(Authentication auth){
		
		List<PlaylistEntity> listPlaylist = playlistService.getAllPlaylistByUserEmail(auth.getName());
		List<PlaylistDto> listPlaylistDto = new ArrayList<>();
		for (PlaylistEntity playlist : listPlaylist) {
			listPlaylistDto.add(PlaylistMapper.toDto(playlist));
		}
		return new ResponseEntity<>(listPlaylistDto, HttpStatus.OK);
	}

	@Operation(summary = "Crea una nueva playlist.")
	@ApiResponse(responseCode = "200", description = "Acceso autorizado")
	@ApiResponse(responseCode = "403", description = "No autorizado")
	@PostMapping
	public ResponseEntity<PlaylistDto> createPlaylist(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Playlist nueva", required = true)
				@RequestBody PlaylistDto playlistDto,
			Authentication auth){
		UserEntity user = userRepository.findByEmail(auth.getName()).get();
		PlaylistEntity playlist = PlaylistMapper.toEntity(playlistDto, user);

		PlaylistEntity newPlaylist = playlistService.createPlaylist(playlist);
		PlaylistDto newPlaylistDto = PlaylistMapper.toDto(newPlaylist);
		
		return new ResponseEntity<>(newPlaylistDto, HttpStatus.OK);
	}

	@Operation(summary = "Actualiza una playlist.")
	@ApiResponse(responseCode = "200", description = "Playlist actualizada")
	@ApiResponse(responseCode = "401", description = "No autorizado")
	@PutMapping
	public ResponseEntity<PlaylistDto> updatePlaylist(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Playlist actualizada, con un id existente", required = true)
				@RequestBody PlaylistDto playlistDtoUpdate,
			Authentication auth){
		UserEntity user = userRepository.findByEmail(auth.getName()).get();
		boolean isPlaylistOwnedByUser = false;
		for (PlaylistEntity p: user.getPlaylists()){
			if (p.getId() == playlistDtoUpdate.getId()){
				isPlaylistOwnedByUser = true;
				break;
			}
		}
		if (!isPlaylistOwnedByUser){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		PlaylistDto playlistDtoUpdated = playlistService.updatePlaylist(playlistDtoUpdate);
		return new ResponseEntity<>(playlistDtoUpdated, HttpStatus.OK);
	}

	@Operation(summary = "Devuelve la playlist con sus canciones.")
	@ApiResponse(responseCode = "200", description = "Playlist proporcionada")
	@ApiResponse(responseCode = "401", description = "No autorizado")
	@GetMapping("/{playlistId}")
	public ResponseEntity<PlaylistDetailDto> getPlaylist(
			@Parameter(description = "id de playlist")
				@PathVariable Long playlistId,
			Authentication auth){

		UserEntity user = userRepository.findByEmail(auth.getName()).get();
		PlaylistEntity playlist = playlistRepository.findById(playlistId).get();
		boolean isPlaylistOwnedByUser = false;
		for (PlaylistEntity p: user.getPlaylists()){
			if (p.getId() == playlistId){
				isPlaylistOwnedByUser = true;
				break;
			}
		}
		if (!isPlaylistOwnedByUser){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		PlaylistDetailDto playlistDetailDto = PlaylistMapper.toDetailDto(playlist);
		return new ResponseEntity<>(playlistDetailDto, HttpStatus.OK);
	}

	@Operation(summary = "Elimina una playlist.")
	@ApiResponse(responseCode = "204", description = "Playlist eliminada con éxito.")
	@ApiResponse(responseCode = "401", description = "No autorizado")
	@DeleteMapping("/{playlistId}")
	public ResponseEntity<Void> deletePlaylist(
			@Parameter(description = "id de playlist")
				@PathVariable Long playlistId,
			Authentication auth){
		UserEntity user = userRepository.findByEmail(auth.getName()).get();
		boolean isPlaylistOwnedByUser = false;
		for (PlaylistEntity p: user.getPlaylists()){
			if (p.getId() == playlistId){
				isPlaylistOwnedByUser = true;
				break;
			}
		}
		if (!isPlaylistOwnedByUser){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		playlistService.deletePlaylist(playlistId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Operation(summary = "Añade una canción a una playlist.")
	@ApiResponse(responseCode = "201", description = "Canción añadida a la playlist con éxito.")
	@ApiResponse(responseCode = "404", description = "Canción no encontrada.")
	@ApiResponse(responseCode = "400", description = "Canción ya añadida en la lista.")
	@ApiResponse(responseCode = "401", description = "No autorizado")
	@PostMapping("/{playlistId}/songs/{songId}")
	public ResponseEntity<Void> addSongToPlaylist(
			@Parameter(description = "id de playlist")
				@PathVariable Long playlistId,
			@Parameter(description = "id de canción")
				@PathVariable Long songId,
			Authentication auth){
		UserEntity user = userRepository.findByEmail(auth.getName()).get();
		boolean isPlaylistOwnedByUser = false;
		for (PlaylistEntity p: user.getPlaylists()){
			if (p.getId() == playlistId){
				isPlaylistOwnedByUser = true;
				break;
			}
		}
		if (!isPlaylistOwnedByUser){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	
		if (!songRepository.existsById(songId)){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		int statusService = playlistService.addSongToPlaylist(playlistId, songId);
		if (statusService == 1){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "Elimina una canción de una playlist.")
	@ApiResponse(responseCode = "204", description = "Playlist eliminada con éxito.")
	@ApiResponse(responseCode = "404", description = "No existe la canción en la playlist.")
	@ApiResponse(responseCode = "401", description = "No autorizado")
	@DeleteMapping("/{playlistId}/songs/{songId}")
	public ResponseEntity<Void> deleteSongFromPlaylist(
			@Parameter(description = "id de playlist")
				@PathVariable Long playlistId,
			@Parameter(description = "id de canción")
				@PathVariable Long songId,
			Authentication auth){
		UserEntity user = userRepository.findByEmail(auth.getName()).get();
		boolean isPlaylistOwnedByUser = false;
		for (PlaylistEntity p: user.getPlaylists()){
			if (p.getId() == playlistId){
				isPlaylistOwnedByUser = true;
				break;
			}
		}
		if (!isPlaylistOwnedByUser){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		int statusService = playlistService.deleteSongFromPlaylist(playlistId, songId);
		if (statusService == 1){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
