package com.uistify.backend.infraestructure.persistence.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.uistify.backend.domain.model.Artist;
import com.uistify.backend.infraestructure.persistence.jpa.entity.ArtistEntity;

@Mapper
public interface ArtistEntityMapper {

	ArtistEntityMapper INSTANCE = Mappers.getMapper(ArtistEntityMapper.class);

	@Mapping(target = "userId", source = "user.id")
	Artist toDomain(ArtistEntity entity);

	@Mapping(target = "user", ignore = true)
	ArtistEntity toEntity(Artist artist);
}
