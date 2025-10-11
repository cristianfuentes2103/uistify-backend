package com.uistify.backend.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uistify.backend.persistence.model.Playlist;
import com.uistify.backend.persistence.model.User;
import com.uistify.backend.persistence.repository.UserRepository;
import com.uistify.backend.presentation.dto.PlaylistDto;
import com.uistify.backend.service.PlaylistService;
import com.uistify.backend.util.PlaylistMapper;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

	@Autowired
	PlaylistService playlistService;

	@Autowired
	UserRepository userRepository;

	@GetMapping
	public ResponseEntity<List<Playlist>> getAllPlaylist(Authentication auth){
		
		List<Playlist> listPlaylist = playlistService.getAllPlaylistByUserEmail(auth.getName());
		return new ResponseEntity<>(listPlaylist, HttpStatus.OK);
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
}
