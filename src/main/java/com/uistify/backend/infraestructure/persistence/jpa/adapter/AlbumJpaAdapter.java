package com.uistify.backend.infraestructure.persistence.jpa.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.uistify.backend.domain.model.Album;
import com.uistify.backend.domain.port.out.AlbumRepository;
import com.uistify.backend.infraestructure.persistence.jpa.mapper.AlbumEntityMapper;
import com.uistify.backend.infraestructure.persistence.jpa.repository.AlbumJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlbumJpaAdapter implements AlbumRepository{

	private static final AlbumEntityMapper ALBUM_MAPPER = AlbumEntityMapper.INSTANCE;
	private final AlbumJpaRepository albumJpaRepository;

	@Override
	public Optional<Album> findById(Long id){
		return albumJpaRepository.findById(id).map(ALBUM_MAPPER::toDomain);
	}

	@Override
	public Album save(Album album){
		return ALBUM_MAPPER.toDomain(albumJpaRepository.save(ALBUM_MAPPER.toEntity(album)));
	}
}
