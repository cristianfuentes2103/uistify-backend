package com.uistify.backend.infraestructure.persistence.jpa.repository;

import com.uistify.backend.infraestructure.persistence.jpa.entity.PlaylistSongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistSongJpaRepository extends JpaRepository<PlaylistSongEntity, Long> {

    boolean existsByPlaylist_IdAndSong_Id(Long playlistId, Long songId);

    Optional<PlaylistSongEntity> findByPlaylist_IdAndSong_Id(Long playlistId, Long songId);

    List<PlaylistSongEntity> findAllByPlaylist_Id(Long playlistId);
}
