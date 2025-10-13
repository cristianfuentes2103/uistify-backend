package com.uistify.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uistify.backend.persistence.model.Playlist;
import com.uistify.backend.persistence.repository.PlaylistRepository;
import com.uistify.backend.persistence.repository.UserRepository;
import com.uistify.backend.presentation.dto.PlaylistDto;
import com.uistify.backend.util.PlaylistMapper;

@Service
public class PlaylistService implements IPlaylistService{

	@Autowired
	PlaylistRepository playlistRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public List<Playlist> getAllPlaylistByUserEmail(String userEmail){
		return playlistRepository.findByUser_Email(userEmail);
	}

	@Override 
	public Playlist createPlaylist(Playlist playlist){
		return playlistRepository.save(playlist);
	}

	public PlaylistDto updatePlaylist(PlaylistDto playlistUpdate){
		Playlist playlist = playlistRepository.findById(playlistUpdate.getId()).get();

		playlist.setTitle(playlistUpdate.getTitle());
		playlist.setDescription(playlistUpdate.getDescription());

		Playlist playlistUpdated = playlistRepository.save(playlist);

		PlaylistDto newPlaylistDto = PlaylistMapper.toDto(playlistUpdated);

		return newPlaylistDto;
	}

	public void deletePlaylist(Long playlistId){
		playlistRepository.deleteById(playlistId);
	}
}
