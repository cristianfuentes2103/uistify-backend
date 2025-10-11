package com.uistify.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uistify.backend.persistence.model.Playlist;
import com.uistify.backend.persistence.repository.PlaylistRepository;
import com.uistify.backend.persistence.repository.UserRepository;

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
}
