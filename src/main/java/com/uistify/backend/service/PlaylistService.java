package com.uistify.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uistify.backend.persistence.model.Playlist;
import com.uistify.backend.persistence.model.PlaylistSong;
import com.uistify.backend.persistence.model.Song;
import com.uistify.backend.persistence.repository.PlaylistRepository;
import com.uistify.backend.persistence.repository.PlaylistSongRepository;
import com.uistify.backend.persistence.repository.SongRepository;
import com.uistify.backend.persistence.repository.UserRepository;
import com.uistify.backend.presentation.dto.PlaylistDto;
import com.uistify.backend.util.PlaylistMapper;

@Service
public class PlaylistService implements IPlaylistService{

	@Autowired
	PlaylistRepository playlistRepository;

	@Autowired
	SongRepository songRepository;

	@Autowired
	PlaylistSongRepository playlistSongRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public List<Playlist> getAllPlaylistByUserEmail(String userEmail){
		return playlistRepository.findByUser_Email(userEmail);
	}

	@Override 
	public Playlist createPlaylist(Playlist playlist){
		return playlistRepository.save(playlist);
	}

	public PlaylistDto updatePlaylist(PlaylistDto playlistUpdate){
		Playlist playlist = playlistRepository.findById(playlistUpdate.getId()).get();

		playlist.setTitle(playlistUpdate.getTitle());
		playlist.setDescription(playlistUpdate.getDescription());

		Playlist playlistUpdated = playlistRepository.save(playlist);

		PlaylistDto newPlaylistDto = PlaylistMapper.toDto(playlistUpdated);

		return newPlaylistDto;
	}

	public void deletePlaylist(Long playlistId){
		playlistRepository.deleteById(playlistId);
	}

	public int addSongToPlaylist(Long playlistId, Long songId){
		Playlist playlist = playlistRepository.findById(playlistId).get();
		Song song = songRepository.findById(songId).get();

		if (playlistSongRepository.existsByPlaylistIdAndSongId(playlistId, songId)){
			return 1;
		}
		PlaylistSong newPlaylistSong = new PlaylistSong();
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
		PlaylistSong playlistSong = playlistSongRepository.findByPlaylistIdAndSongId(playlistId, songId);
		playlistSongRepository.deleteById(playlistSong.getId());
		return 0;
	}
}
