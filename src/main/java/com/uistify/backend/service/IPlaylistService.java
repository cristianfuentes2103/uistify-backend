package com.uistify.backend.service;

import java.util.List;

import com.uistify.backend.persistence.model.Playlist;

public interface IPlaylistService {
	List<Playlist> getAllPlaylistByUserEmail(String userEmail);
	Playlist createPlaylist(Playlist playlist);
}
