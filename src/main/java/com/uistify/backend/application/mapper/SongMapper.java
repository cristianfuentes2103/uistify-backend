package com.uistify.backend.application.mapper;

import com.uistify.backend.domain.model.Song;
import com.uistify.backend.presentation.rest.dto.SongDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    SongDto toDto(Song song);

    Song toDomain(SongDto dto);
}
