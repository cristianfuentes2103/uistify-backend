package com.uistify.backend.presentation.rest.dto;

import java.util.ArrayList;
import java.util.List;

import com.uistify.backend.infraestructure.persistence.jpa.Entity.SongEntity;

import lombok.Data;

@Data
public class PlaylistDetailDto {
	private Long id;
	private String title;
	private String description;
	private List<SongEntity> songs = new ArrayList<>();
}
