package com.uistify.backend.infraestructure.persistence.jpa.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.uistify.backend.domain.port.out.ArtistRepository;
import com.uistify.backend.infraestructure.persistence.jpa.entity.ArtistEntity;
import com.uistify.backend.infraestructure.persistence.jpa.entity.UserEntity;
import com.uistify.backend.infraestructure.persistence.jpa.mapper.ArtistEntityMapper;
import com.uistify.backend.infraestructure.persistence.jpa.repository.ArtistJpaRepository;
import com.uistify.backend.infraestructure.persistence.jpa.repository.UserJpaRepository;
import com.uistify.backend.domain.model.Artist;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArtistJpaAdapter implements ArtistRepository{

	private static final ArtistEntityMapper ARTIST_MAPPER = ArtistEntityMapper.INSTANCE;

	private final ArtistJpaRepository artistJpaRepository;
	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<Artist> findById(Long id){
		return artistJpaRepository.findById(id).map(ARTIST_MAPPER::toDomain);
	}

	@Override
	public Optional<Artist> findByUserId(Long userId){
		return artistJpaRepository.findByUser_Id(userId).map(ARTIST_MAPPER::toDomain);
	}

	@Override
	public Artist save(Artist artist){
		ArtistEntity entity = ARTIST_MAPPER.toEntity(artist);

		UserEntity userEntity = userJpaRepository.findById(artist.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("User not found"));

		entity.setUser(userEntity);

		ArtistEntity saved = artistJpaRepository.save(entity);
		return ARTIST_MAPPER.toDomain(saved);
	}
}
