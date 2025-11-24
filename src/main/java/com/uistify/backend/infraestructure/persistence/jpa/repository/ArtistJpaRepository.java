package com.uistify.backend.infraestructure.persistence.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uistify.backend.infraestructure.persistence.jpa.entity.ArtistEntity;

public interface ArtistJpaRepository extends JpaRepository<ArtistEntity, Long> {

	Optional<ArtistEntity> findByUser_Id(Long userId);
}
