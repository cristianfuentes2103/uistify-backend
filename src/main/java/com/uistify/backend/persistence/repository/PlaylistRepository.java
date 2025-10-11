package com.uistify.backend.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uistify.backend.persistence.model.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
	List<Playlist> findByUser_Id(Long id);
	List<Playlist> findByUser_Email(String email);
}
