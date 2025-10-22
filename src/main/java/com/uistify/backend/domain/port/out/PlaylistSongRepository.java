package com.uistify.backend.domain.port.out;

import com.uistify.backend.domain.model.PlaylistSong;

import java.util.Optional;

public interface PlaylistSongRepository {

    boolean existsByPlaylistIdAndSongId(Long playlistId, Long songId);

    PlaylistSong save(PlaylistSong playlistSong);

    Optional<PlaylistSong> findByPlaylistIdAndSongId(Long playlistId, Long songId);

    void deleteById(Long id);
}
