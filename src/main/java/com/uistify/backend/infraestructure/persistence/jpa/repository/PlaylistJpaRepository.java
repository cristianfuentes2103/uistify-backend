package com.uistify.backend.infraestructure.persistence.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uistify.backend.infraestructure.persistence.jpa.Entity.PlaylistEntity;

public interface PlaylistJpaRepository extends JpaRepository<PlaylistEntity, Long>{
	List<PlaylistEntity> findByUser_Id(Long id);
	List<PlaylistEntity> findByUser_Email(String email);
}
