package com.uistify.backend.domain.port.out;

import com.uistify.backend.domain.model.Song;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SongRepository {

    List<Song> findAll(Pageable pageable);

    Optional<Song> findById(Long id);

    List<Song> findByTitleContaining(String fragment);

    boolean existsById(Long id);

	Song save(Song song);
}
