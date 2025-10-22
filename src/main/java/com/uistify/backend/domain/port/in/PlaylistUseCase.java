package com.uistify.backend.domain.port.in;

import com.uistify.backend.domain.model.Playlist;

import java.util.List;

public interface PlaylistUseCase {

    List<Playlist> getAllPlaylistsByUserEmail(String userEmail);

    Playlist createPlaylist(String userEmail, Playlist playlist);

    Playlist updatePlaylist(String userEmail, Playlist playlist);

    Playlist getPlaylistDetail(String userEmail, Long playlistId);

    void deletePlaylist(String userEmail, Long playlistId);

    void addSongToPlaylist(String userEmail, Long playlistId, Long songId);

    void deleteSongFromPlaylist(String userEmail, Long playlistId, Long songId);
}
