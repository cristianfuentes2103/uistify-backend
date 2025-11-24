package com.uistify.backend.infraestructure.persistence.jpa.adapter;

import com.uistify.backend.domain.model.Playlist;
import com.uistify.backend.domain.port.out.PlaylistRepository;
import com.uistify.backend.infraestructure.persistence.jpa.entity.PlaylistEntity;
import com.uistify.backend.infraestructure.persistence.jpa.mapper.PlaylistEntityMapper;
import com.uistify.backend.infraestructure.persistence.jpa.repository.PlaylistJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class PlaylistJpaAdapter implements PlaylistRepository {

    private static final PlaylistEntityMapper PLAYLIST_ENTITY_MAPPER = PlaylistEntityMapper.INSTANCE;

    private final PlaylistJpaRepository playlistJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Playlist> findByUserEmail(String email) {
        return playlistJpaRepository.findByUser_Email(email).stream()
                .map(playlistEntity -> {
                    playlistEntity.getSongs().size();
                    return PLAYLIST_ENTITY_MAPPER.toDomain(playlistEntity);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Playlist> findById(Long id) {
        return playlistJpaRepository.findById(id)
                .map(entity -> {
                    entity.getSongs().size();
                    return PLAYLIST_ENTITY_MAPPER.toDomain(entity);
                });
    }

	@Override
	public List<Playlist> findAllByPublicPlaylistTrue(){
		return playlistJpaRepository.findAllByPublicPlaylistTrue().stream()
			.map(PLAYLIST_ENTITY_MAPPER::toDomain)
			.collect(Collectors.toList());
	}

    @Override
    public Playlist save(Playlist playlist) {
        PlaylistEntity entity = playlist.getId() != null
                ? playlistJpaRepository.findById(playlist.getId()).orElseGet(PlaylistEntity::new)
                : new PlaylistEntity();

        PLAYLIST_ENTITY_MAPPER.updateEntityFromDomain(playlist, entity);

        PlaylistEntity saved = playlistJpaRepository.save(entity);
        saved.getSongs().size();
        return PLAYLIST_ENTITY_MAPPER.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        playlistJpaRepository.deleteById(id);
    }
}
