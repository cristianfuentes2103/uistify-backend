package com.uistify.backend.infraestructure.persistence.jpa.adapter;

import com.uistify.backend.domain.model.PlaylistSong;
import com.uistify.backend.domain.port.out.PlaylistSongRepository;
import com.uistify.backend.infraestructure.persistence.jpa.entity.PlaylistSongEntity;
import com.uistify.backend.infraestructure.persistence.jpa.mapper.PlaylistSongEntityMapper;
import com.uistify.backend.infraestructure.persistence.jpa.repository.PlaylistSongJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlaylistSongJpaAdapter implements PlaylistSongRepository {

    private static final PlaylistSongEntityMapper PLAYLIST_SONG_ENTITY_MAPPER = PlaylistSongEntityMapper.INSTANCE;

    private final PlaylistSongJpaRepository playlistSongJpaRepository;

    @Override
    public boolean existsByPlaylistIdAndSongId(Long playlistId, Long songId) {
        return playlistSongJpaRepository.existsByPlaylist_IdAndSong_Id(playlistId, songId);
    }

    @Override
    public PlaylistSong save(PlaylistSong playlistSong) {
        return PLAYLIST_SONG_ENTITY_MAPPER.toDomain(
                playlistSongJpaRepository.save(PLAYLIST_SONG_ENTITY_MAPPER.toEntity(playlistSong)));
    }

    @Override
    public Optional<PlaylistSong> findByPlaylistIdAndSongId(Long playlistId, Long songId) {
        return playlistSongJpaRepository.findByPlaylist_IdAndSong_Id(playlistId, songId)
                .map(PLAYLIST_SONG_ENTITY_MAPPER::toDomain);
    }

    @Override
    public void deleteAllByPlayListId(Long playListId) {
        List<PlaylistSongEntity> playlistSongEntities = playlistSongJpaRepository
                .findAllByPlaylist_Id(playListId);
        playlistSongJpaRepository.deleteAll(playlistSongEntities);
    }

    @Override
    public void deleteById(Long id) {
        playlistSongJpaRepository.deleteById(id);
    }
}
