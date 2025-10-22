package com.uistify.backend.infraestructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uistify.backend.infraestructure.persistence.jpa.entity.PlaylistSongEntity;

import java.util.Optional;

public interface PlaylistSongJpaRepository extends JpaRepository<PlaylistSongEntity, Long> {

    boolean existsByPlaylist_IdAndSong_Id(Long playlistId, Long songId);

    Optional<PlaylistSongEntity> findByPlaylist_IdAndSong_Id(Long playlistId, Long songId);
}
