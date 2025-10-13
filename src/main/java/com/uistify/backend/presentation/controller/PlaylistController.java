package com.uistify.backend.presentation.controller;

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

import com.uistify.backend.persistence.model.Playlist;
import com.uistify.backend.persistence.model.User;
import com.uistify.backend.persistence.repository.PlaylistRepository;
import com.uistify.backend.persistence.repository.SongRepository;
import com.uistify.backend.persistence.repository.UserRepository;
import com.uistify.backend.presentation.dto.PlaylistDetailDto;
import com.uistify.backend.presentation.dto.PlaylistDto;
import com.uistify.backend.service.PlaylistService;
import com.uistify.backend.util.PlaylistMapper;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

	@Autowired
	PlaylistService playlistService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PlaylistRepository playlistRepository;

	@Autowired
	SongRepository songRepository;

	@GetMapping
	public ResponseEntity<List<PlaylistDto>> getAllPlaylist(Authentication auth){
		
		List<Playlist> listPlaylist = playlistService.getAllPlaylistByUserEmail(auth.getName());
		List<PlaylistDto> listPlaylistDto = new ArrayList<>();
		for (Playlist playlist : listPlaylist) {
			listPlaylistDto.add(PlaylistMapper.toDto(playlist));
		}
		return new ResponseEntity<>(listPlaylistDto, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PlaylistDto> createPlaylist(
			@RequestBody PlaylistDto playlistDto,
			Authentication auth){
		User user = userRepository.findByEmail(auth.getName()).get();
		Playlist playlist = PlaylistMapper.toEntity(playlistDto, user);

		Playlist newPlaylist = playlistService.createPlaylist(playlist);
		PlaylistDto newPlaylistDto = PlaylistMapper.toDto(newPlaylist);
		
		return new ResponseEntity<>(newPlaylistDto, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<PlaylistDto> updatePlaylist(
			@RequestBody PlaylistDto playlistDtoUpdate,
			Authentication auth){
		User user = userRepository.findByEmail(auth.getName()).get();
		boolean isPlaylistOwnedByUser = false;
		for (Playlist p: user.getPlaylists()){
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

	@GetMapping("/{playlistId}")
	public ResponseEntity<PlaylistDetailDto> getPlaylist(
			@PathVariable Long playlistId,
			Authentication auth){

		User user = userRepository.findByEmail(auth.getName()).get();
		Playlist playlist = playlistRepository.findById(playlistId).get();
		boolean isPlaylistOwnedByUser = false;
		for (Playlist p: user.getPlaylists()){
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

	@DeleteMapping("/{playlistId}")
	public ResponseEntity<Void> deletePlaylist(
			@PathVariable Long playlistId,
			Authentication auth){
		User user = userRepository.findByEmail(auth.getName()).get();
		boolean isPlaylistOwnedByUser = false;
		for (Playlist p: user.getPlaylists()){
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

	@PostMapping("/{playlistId}/songs/{songId}")
	public ResponseEntity<Void> addSongToPlaylist(
			@PathVariable Long playlistId,
			@PathVariable Long songId,
			Authentication auth){
		User user = userRepository.findByEmail(auth.getName()).get();
		boolean isPlaylistOwnedByUser = false;
		for (Playlist p: user.getPlaylists()){
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

	@DeleteMapping("/{playlistId}/songs/{songId}")
	public ResponseEntity<Void> deleteSongFromPlaylist(
			@PathVariable Long playlistId,
			@PathVariable Long songId,
			Authentication auth){
		User user = userRepository.findByEmail(auth.getName()).get();
		boolean isPlaylistOwnedByUser = false;
		for (Playlist p: user.getPlaylists()){
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
