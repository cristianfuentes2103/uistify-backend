package com.uistify.backend.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.uistify.backend.domain.port.in.SongUseCase;
import com.uistify.backend.infraestructure.persistence.jpa.Entity.SongEntity;
import com.uistify.backend.infraestructure.persistence.jpa.repository.SongJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SongService implements SongUseCase {

	@Autowired
	SongJpaRepository songRepository;

	@Override
	public List<SongEntity> getAllSongs(Pageable pageable){
		return songRepository.findAll(pageable).getContent();
	}

	@Override
	public Optional<SongEntity> getSongById(Long id){
		return songRepository.findById(id);
	}

	@Override
	public List<SongEntity> findByTitleContaining(String fragment){
		return songRepository.findByTitleContainingIgnoreCase(fragment);
	}

	@Override
	public List<SongEntity> findByAlbumContaining(String fragment){
		return songRepository.findByAlbumContainingIgnoreCase(fragment);
	}

	@Override
	public List<SongEntity> findByArtistContaining(String fragment){
		return songRepository.findByArtistContainingIgnoreCase(fragment);
	}
}
