package com.uistify.backend.presentation.dto;

import java.util.ArrayList;
import java.util.List;

import com.uistify.backend.persistence.model.Song;

import lombok.Data;

@Data
public class PlaylistDetailDto {
	private Long id;
	private String title;
	private String description;
	private List<Song> songs = new ArrayList<>();
}
