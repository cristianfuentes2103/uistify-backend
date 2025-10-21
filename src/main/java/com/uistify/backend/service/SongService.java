package com.uistify.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uistify.backend.persistence.model.Song;
import com.uistify.backend.persistence.repository.SongRepository;

@Service
public class SongService implements ISongService {

	@Autowired
	SongRepository songRepository;

	@Override
	public Page<Song> getAllSongs(Pageable pageable){
		return songRepository.findAll(pageable);
	}

	@Override
	public Optional<Song> getSongById(Long id){
		return songRepository.findById(id);
	}

	@Override
	public List<Song> findByTitleContaining(String fragment){
		return songRepository.findByTitleContainingIgnoreCase(fragment);
	}

	@Override
	public List<Song> findByAlbumContaining(String fragment){
		return songRepository.findByAlbumContainingIgnoreCase(fragment);
	}

	@Override
	public List<Song> findByArtistContaining(String fragment){
		return songRepository.findByArtistContainingIgnoreCase(fragment);
	}
}
