package com.uistify.backend.infraestructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uistify.backend.infraestructure.persistence.jpa.entity.SongEntity;

import java.util.List;

public interface SongJpaRepository extends JpaRepository<SongEntity, Long>{
	List<SongEntity> findByTitleContainingIgnoreCase(String fragment);
	List<SongEntity> findByArtistContainingIgnoreCase(String fragment);
	List<SongEntity> findByAlbumContainingIgnoreCase(String fragment);
}
