package com.uistify.backend.domain.port.out;

import com.uistify.backend.domain.model.Album;

import java.util.Optional;

public interface AlbumRepository {

	Optional<Album> findById(Long id);
	Album save(Album album);
}
