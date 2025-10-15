package com.uistify.backend.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uistify.backend.persistence.model.Song;

public interface SongRepository extends JpaRepository<Song, Long>{
	List<Song> findByTitleContainingIgnoreCase(String fragment);
	List<Song> findByArtistContainingIgnoreCase(String fragment);
	List<Song> findByAlbumContainingIgnoreCase(String fragment);
}
