package com.uistify.backend.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uistify.backend.application.mapper.PlaylistMapper;
import com.uistify.backend.domain.port.in.PlaylistUseCase;
import com.uistify.backend.infraestructure.persistence.jpa.Entity.PlaylistEntity;
import com.uistify.backend.infraestructure.persistence.jpa.Entity.PlaylistSongEntity;
import com.uistify.backend.infraestructure.persistence.jpa.Entity.SongEntity;
import com.uistify.backend.infraestructure.persistence.jpa.repository.PlaylistJpaRepository;
import com.uistify.backend.infraestructure.persistence.jpa.repository.PlaylistSongJpaRepository;
import com.uistify.backend.infraestructure.persistence.jpa.repository.SongJpaRepository;
import com.uistify.backend.infraestructure.persistence.jpa.repository.UserJpaRepository;
import com.uistify.backend.presentation.rest.dto.PlaylistDto;

@Service
@Transactional
public class PlaylistService implements PlaylistUseCase{

	@Autowired
	PlaylistJpaRepository playlistRepository;

	@Autowired
	SongJpaRepository songRepository;

	@Autowired
	PlaylistSongJpaRepository playlistSongRepository;

	@Autowired
	UserJpaRepository userRepository;

	@Override
	public List<PlaylistEntity> getAllPlaylistByUserEmail(String userEmail){
		return playlistRepository.findByUser_Email(userEmail);
	}

	@Override 
	public PlaylistEntity createPlaylist(PlaylistEntity playlist){
		return playlistRepository.save(playlist);
	}

	public PlaylistDto updatePlaylist(PlaylistDto playlistUpdate){
		PlaylistEntity playlist = playlistRepository.findById(playlistUpdate.getId()).get();

		playlist.setTitle(playlistUpdate.getTitle());
		playlist.setDescription(playlistUpdate.getDescription());

		PlaylistEntity playlistUpdated = playlistRepository.save(playlist);

		return PlaylistMapper.toDto(playlistUpdated);
	}

	public void deletePlaylist(Long playlistId){
		playlistRepository.deleteById(playlistId);
	}

	public int addSongToPlaylist(Long playlistId, Long songId){
		PlaylistEntity playlist = playlistRepository.findById(playlistId).get();
		SongEntity song = songRepository.findById(songId).get();

		if (playlistSongRepository.existsByPlaylistIdAndSongId(playlistId, songId)){
			return 1;
		}
		PlaylistSongEntity newPlaylistSong = new PlaylistSongEntity();
		newPlaylistSong.setPlaylist(playlist);
		newPlaylistSong.setSong(song);
		newPlaylistSong.setNumberSong(playlist.getSongs().size()+1);

		playlistSongRepository.save(newPlaylistSong);
		return 0;
	}

	public int deleteSongFromPlaylist(Long playlistId, Long songId){
		if (!playlistSongRepository.existsByPlaylistIdAndSongId(playlistId, songId)){
			return 1;
		}

		PlaylistSongEntity playlistSong = playlistSongRepository.findByPlaylistIdAndSongId(playlistId, songId);

		playlistSongRepository.deleteById(playlistSong.getId());
		return 0;
	}
}
