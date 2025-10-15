package com.uistify.backend.util;

import com.uistify.backend.persistence.model.Playlist;
import com.uistify.backend.persistence.model.PlaylistSong;
import com.uistify.backend.persistence.model.User;
import com.uistify.backend.presentation.dto.PlaylistDetailDto;
import com.uistify.backend.presentation.dto.PlaylistDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlaylistMapper {

	public static Playlist toEntity(PlaylistDto dto, User user){
		Playlist entity = new Playlist();
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		entity.setUser(user);
		
		return entity;
	}

	public static PlaylistDto toDto(Playlist entity){
		PlaylistDto dto = new PlaylistDto();
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());

		return dto;
	}

	public static PlaylistDetailDto toDetailDto(Playlist entity){
		PlaylistDetailDto dto = new PlaylistDetailDto();

		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		for (PlaylistSong song : entity.getSongs()) {
			dto.getSongs().add(song.getSong());
		}

		return dto;
	}
}
