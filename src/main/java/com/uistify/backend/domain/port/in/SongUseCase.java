package com.uistify.backend.domain.port.in;

import com.uistify.backend.domain.model.Song;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SongUseCase {

    List<Song> getAllSongs(Pageable pageable);

    Optional<Song> getSongById(Long id);

    List<Song> findByTitleContaining(String fragment);
}
