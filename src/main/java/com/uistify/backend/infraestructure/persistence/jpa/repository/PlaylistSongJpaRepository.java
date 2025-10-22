package com.uistify.backend.infraestructure.persistence.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uistify.backend.infraestructure.persistence.jpa.Entity.PlaylistSongEntity;

public interface PlaylistSongJpaRepository extends JpaRepository<PlaylistSongEntity, Long> {
	List<PlaylistSongEntity> findByPlaylist_Id(Long id);
	boolean existsByPlaylistIdAndSongId(Long playlistId, Long songId);
	PlaylistSongEntity findByPlaylistIdAndSongId(Long playlistId, Long songId);
}
