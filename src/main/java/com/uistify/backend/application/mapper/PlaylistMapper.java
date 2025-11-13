package com.uistify.backend.application.mapper;

import com.uistify.backend.domain.model.Playlist;
import com.uistify.backend.domain.model.PlaylistSong;
import com.uistify.backend.presentation.rest.dto.PlaylistDetailDto;
import com.uistify.backend.presentation.rest.dto.PlaylistDto;
import com.uistify.backend.presentation.rest.dto.SongDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = SongMapper.class)
public interface PlaylistMapper {

    PlaylistMapper INSTANCE = Mappers.getMapper(PlaylistMapper.class);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "songs", ignore = true)
    Playlist toDomain(PlaylistDto dto);

    PlaylistDto toDto(Playlist playlist);

    @Mapping(target = "songs", ignore = true)
    PlaylistDetailDto toDetailDtoBase(Playlist playlist);

    default PlaylistDetailDto toDetailDto(Playlist playlist) {
        PlaylistDetailDto dto = toDetailDtoBase(playlist);
        if (dto == null) {
            return null;
        }
        List<SongDto> songs = playlist == null || playlist.getSongs() == null
                ? new ArrayList<>()
                : playlist.getSongs().stream()
                .sorted(Comparator.comparingInt(PlaylistSong::getTrackNumber))
                .map(PlaylistSong::getSong)
                .map(SongMapper.INSTANCE::toDto)
                .collect(Collectors.toCollection(ArrayList::new));
        dto.setSongs(songs);
        return dto;
    }
}
