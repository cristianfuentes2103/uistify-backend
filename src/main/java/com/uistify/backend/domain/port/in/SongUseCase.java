package com.uistify.backend.domain.port.in;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.uistify.backend.infraestructure.persistence.jpa.Entity.SongEntity;

public interface SongUseCase {

	List<SongEntity> getAllSongs(Pageable pageable);
	Optional<SongEntity> getSongById(Long id);
	List<SongEntity> findByTitleContaining(String fragment);
	List<SongEntity> findByArtistContaining(String fragment);
	List<SongEntity> findByAlbumContaining(String fragment);
}
