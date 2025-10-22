package com.uistify.backend.infraestructure.persistence.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uistify.backend.infraestructure.persistence.jpa.Entity.SongEntity;

public interface SongJpaRepository extends JpaRepository<SongEntity, Long>{
	List<SongEntity> findByTitleContainingIgnoreCase(String fragment);
	List<SongEntity> findByArtistContainingIgnoreCase(String fragment);
	List<SongEntity> findByAlbumContainingIgnoreCase(String fragment);
}
