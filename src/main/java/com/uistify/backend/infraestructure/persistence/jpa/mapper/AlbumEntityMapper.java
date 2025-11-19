package com.uistify.backend.infraestructure.persistence.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uistify.backend.domain.model.Album;
import com.uistify.backend.infraestructure.persistence.jpa.entity.AlbumEntity;
import com.uistify.backend.infraestructure.persistence.jpa.entity.ArtistEntity;

@Mapper
public interface AlbumEntityMapper {

	AlbumEntityMapper INSTANCE = Mappers.getMapper(AlbumEntityMapper.class);

	@Mapping(target = "artistId", source = "artist.id")
	Album toDomain(AlbumEntity entity);

	@Mapping(target = "artist", source = "artistId", qualifiedByName = "artistIdToArtist")
	AlbumEntity toEntity(Album album);

	@Named("artistIdToArtist")
	public static ArtistEntity artistIdToArtist(Long id){
		if (id == null) return null;
		ArtistEntity artist = new ArtistEntity();
		artist.setId(id);
		return artist;
	}
}
