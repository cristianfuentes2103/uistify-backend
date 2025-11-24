package com.uistify.backend.presentation.rest.dto;

import lombok.Data;

@Data
public class ArtistDto {

	private Long id;
	private String name;
	private String country;
	private String portraitUrl;
	private Long userId;
}
