package com.uistify.backend.infraestructure.persistence.jpa.mapper;

import com.uistify.backend.domain.model.Playlist;
import com.uistify.backend.domain.model.PlaylistSong;
import com.uistify.backend.infraestructure.persistence.jpa.entity.PlaylistEntity;
import com.uistify.backend.infraestructure.persistence.jpa.entity.UserEntity;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = PlaylistSongEntityMapper.class)
public interface PlaylistEntityMapper {

    PlaylistEntityMapper INSTANCE = Mappers.getMapper(PlaylistEntityMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "songs", ignore = true)
    Playlist toDomainBase(PlaylistEntity entity);

    default Playlist toDomain(PlaylistEntity entity) {
        Playlist playlist = toDomainBase(entity);
        if (playlist == null) {
            return null;
        }
        List<PlaylistSong> songs = entity == null || entity.getSongs() == null
                ? List.of()
                : entity.getSongs().stream()
                        .map(PlaylistSongEntityMapper.INSTANCE::toDomain)
                        .collect(Collectors.toList());
        return playlist.toBuilder().songs(songs).build();
    }

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "songs", ignore = true)
    void updateEntityFromDomain(Playlist playlist, @MappingTarget PlaylistEntity entity);

    @AfterMapping
    default void attachUser(Playlist playlist, @MappingTarget PlaylistEntity entity) {
        if (playlist != null && playlist.getUserId() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(playlist.getUserId());
            entity.setUser(userEntity);
        }
    }
}
