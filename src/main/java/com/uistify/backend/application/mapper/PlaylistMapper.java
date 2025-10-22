package com.uistify.backend.application.mapper;

import com.uistify.backend.infraestructure.persistence.jpa.Entity.PlaylistEntity;
import com.uistify.backend.infraestructure.persistence.jpa.Entity.PlaylistSongEntity;
import com.uistify.backend.infraestructure.persistence.jpa.Entity.UserEntity;
import com.uistify.backend.presentation.rest.dto.PlaylistDetailDto;
import com.uistify.backend.presentation.rest.dto.PlaylistDto;


public class PlaylistMapper {

	private PlaylistMapper(){}

	public static PlaylistEntity toEntity(PlaylistDto dto, UserEntity user){
		PlaylistEntity entity = new PlaylistEntity();
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		entity.setUser(user);
		
		return entity;
	}

	public static PlaylistDto toDto(PlaylistEntity entity){
		PlaylistDto dto = new PlaylistDto();
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());

		return dto;
	}

	public static PlaylistDetailDto toDetailDto(PlaylistEntity entity){
		PlaylistDetailDto dto = new PlaylistDetailDto();

		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		for (PlaylistSongEntity song : entity.getSongs()) {
			dto.getSongs().add(song.getSong());
		}

		return dto;
	}
}
