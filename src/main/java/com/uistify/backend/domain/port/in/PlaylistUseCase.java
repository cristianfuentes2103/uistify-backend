package com.uistify.backend.domain.port.in;

import com.uistify.backend.domain.model.Playlist;
import com.uistify.backend.domain.model.Song;

import java.util.List;

public interface PlaylistUseCase {

	List<Playlist> getAllPublicPlaylists();

	Playlist getPublicPlaylist(Long playlistId);

    List<Song> getSongsFromPublicPlaylist(Long playlistId);

    List<Playlist> getAllPlaylistsByUserEmail(String userEmail);

    Playlist createPlaylist(String userEmail, Playlist playlist);

    Playlist updatePlaylist(String userEmail, Playlist playlist);

    Playlist getPlaylistDetail(String userEmail, Long playlistId);

    List<Song> getSongsFromPlaylist(String userEmail, Long playlistId);

    void deletePlaylist(String userEmail, Long playlistId);

    void addSongToPlaylist(String userEmail, Long playlistId, Long songId);

    void deleteSongFromPlaylist(String userEmail, Long playlistId, Long songId);
}
