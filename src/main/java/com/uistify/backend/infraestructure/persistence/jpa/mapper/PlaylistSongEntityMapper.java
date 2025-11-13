package com.uistify.backend.infraestructure.persistence.jpa.mapper;

import com.uistify.backend.domain.model.PlaylistSong;
import com.uistify.backend.infraestructure.persistence.jpa.entity.PlaylistEntity;
import com.uistify.backend.infraestructure.persistence.jpa.entity.PlaylistSongEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = SongEntityMapper.class)
public interface PlaylistSongEntityMapper {

    PlaylistSongEntityMapper INSTANCE = Mappers.getMapper(PlaylistSongEntityMapper.class);

    @Mapping(target = "playlistId", source = "playlist.id")
    @Mapping(target = "trackNumber", source = "numberSong")
    PlaylistSong toDomain(PlaylistSongEntity entity);

    @Mapping(target = "numberSong", source = "trackNumber")
    @Mapping(target = "playlist", ignore = true)
    PlaylistSongEntity toEntity(PlaylistSong playlistSong);

    @AfterMapping
    default void attachPlaylist(PlaylistSong playlistSong, @MappingTarget PlaylistSongEntity entity) {
        if (playlistSong != null && playlistSong.getPlaylistId() != null) {
            PlaylistEntity playlist = new PlaylistEntity();
            playlist.setId(playlistSong.getPlaylistId());
            entity.setPlaylist(playlist);
        }
    }
}
