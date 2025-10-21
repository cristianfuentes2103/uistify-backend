package com.uistify.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.uistify.backend.persistence.model.Song;

public interface ISongService {

	List<Song> getAllSongs(Pageable pageable);
	Optional<Song> getSongById(Long id);
	List<Song> findByTitleContaining(String fragment);
	List<Song> findByArtistContaining(String fragment);
	List<Song> findByAlbumContaining(String fragment);
}
