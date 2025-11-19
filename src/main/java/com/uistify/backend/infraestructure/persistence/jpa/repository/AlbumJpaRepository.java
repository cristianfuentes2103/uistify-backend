package com.uistify.backend.infraestructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uistify.backend.infraestructure.persistence.jpa.entity.AlbumEntity;

public interface AlbumJpaRepository extends JpaRepository<AlbumEntity, Long>{

	
}
