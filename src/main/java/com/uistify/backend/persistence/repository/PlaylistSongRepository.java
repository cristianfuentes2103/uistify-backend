package com.uistify.backend.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uistify.backend.persistence.model.PlaylistSong;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long> {
	List<PlaylistSong> findByPlaylist_Id(Long id);
	boolean existsByPlaylistIdAndSongId(Long playlistId, Long songId);
}
