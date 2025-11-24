package com.uistify.backend.application.mapper;

import com.uistify.backend.domain.model.Song;
import com.uistify.backend.presentation.rest.dto.SongDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

	@Mapping(target = "artist", ignore = true)
    SongDto toDto(Song song);

	@Mapping(target = "artistId", ignore = true)
    Song toDomain(SongDto dto);
}
