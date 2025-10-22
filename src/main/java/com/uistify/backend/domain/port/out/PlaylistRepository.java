package com.uistify.backend.domain.port.out;

import com.uistify.backend.domain.model.Playlist;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository {

    List<Playlist> findByUserEmail(String email);

    Optional<Playlist> findById(Long id);

    Playlist save(Playlist playlist);

    void deleteById(Long id);
}
