package com.uistify.backend.service;

import java.util.List;

import com.uistify.backend.persistence.model.Song;

public interface ISongService {

	List<Song> getAllSongs();
	List<Song> findByTitleContaining(String fragment);
	List<Song> findByArtistContaining(String fragment);
	List<Song> findByAlbumContaining(String fragment);
}
