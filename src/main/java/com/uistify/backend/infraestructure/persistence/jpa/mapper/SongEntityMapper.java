package com.uistify.backend.infraestructure.persistence.jpa.mapper;

import com.uistify.backend.domain.model.Song;
import com.uistify.backend.infraestructure.persistence.jpa.entity.SongEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SongEntityMapper {

    SongEntityMapper INSTANCE = Mappers.getMapper(SongEntityMapper.class);

    Song toDomain(SongEntity entity);

    SongEntity toEntity(Song song);
}
