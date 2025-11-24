package com.uistify.backend.domain.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Album {

	Long id;
	String name;
	LocalDate releaseDate;
	String coverImageUrl;
	Long artistId;
}
