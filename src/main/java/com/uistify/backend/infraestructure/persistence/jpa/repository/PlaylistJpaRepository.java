package com.uistify.backend.infraestructure.persistence.jpa.repository;

import com.uistify.backend.infraestructure.persistence.jpa.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistJpaRepository extends JpaRepository<PlaylistEntity, Long> {
    List<PlaylistEntity> findByUser_Id(Long id);

    List<PlaylistEntity> findByUser_Email(String email);

	List<PlaylistEntity> findAllByPublicPlaylistTrue();
}
