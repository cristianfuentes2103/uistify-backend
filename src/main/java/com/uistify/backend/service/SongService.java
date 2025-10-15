package com.uistify.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uistify.backend.persistence.model.Song;
import com.uistify.backend.persistence.repository.SongRepository;

@Service
public class SongService implements ISongService {

	@Autowired
	SongRepository songRepository;

	@Override
	public List<Song> getAllSongs(){
		return songRepository.findAll();
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
