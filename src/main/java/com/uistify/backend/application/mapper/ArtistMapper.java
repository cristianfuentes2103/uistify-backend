package com.uistify.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.uistify.backend.domain.model.Artist;
import com.uistify.backend.presentation.rest.dto.ArtistDto;

@Mapper
public interface ArtistMapper {

	ArtistMapper INSTANCE = Mappers.getMapper(ArtistMapper.class);

	ArtistDto toDto(Artist artist);

	Artist toDomain(ArtistDto dto);
}
