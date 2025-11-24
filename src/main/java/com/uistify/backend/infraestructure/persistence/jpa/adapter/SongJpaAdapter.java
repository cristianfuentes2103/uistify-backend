package com.uistify.backend.infraestructure.persistence.jpa.adapter;

import com.uistify.backend.domain.model.Song;
import com.uistify.backend.domain.port.out.SongRepository;
import com.uistify.backend.infraestructure.persistence.jpa.mapper.SongEntityMapper;
import com.uistify.backend.infraestructure.persistence.jpa.repository.SongJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SongJpaAdapter implements SongRepository {

    private static final SongEntityMapper SONG_ENTITY_MAPPER = SongEntityMapper.INSTANCE;

    private final SongJpaRepository songJpaRepository;

    @Override
    public List<Song> findAll(Pageable pageable) {
        return songJpaRepository.findAll(pageable).getContent().stream()
                .map(SONG_ENTITY_MAPPER::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Song> findById(Long id) {
        return songJpaRepository.findById(id).map(SONG_ENTITY_MAPPER::toDomain);
    }

    @Override
    public List<Song> findByTitleContaining(String fragment) {
        return songJpaRepository.findByTitleContainingIgnoreCase(fragment).stream()
                .map(SONG_ENTITY_MAPPER::toDomain)
                .collect(Collectors.toList());
    }

	@Override
	public Song save(Song song){
		return SONG_ENTITY_MAPPER.toDomain(songJpaRepository.save(SONG_ENTITY_MAPPER.toEntity(song)));
	}

    @Override
    public boolean existsById(Long id) {
        return songJpaRepository.existsById(id);
    }
}
