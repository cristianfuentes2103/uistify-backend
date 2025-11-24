package com.uistify.backend.domain.port.out;

import java.util.Optional;

import com.uistify.backend.domain.model.Artist;

public interface ArtistRepository {

	Optional<Artist> findById(Long id);
	Optional<Artist> findByUserId(Long userId);
	Artist save(Artist artist);
}
