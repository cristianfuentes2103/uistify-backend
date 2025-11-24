package com.uistify.backend.infraestructure.persistence.jpa.mapper;

import com.uistify.backend.domain.model.Song;
import com.uistify.backend.infraestructure.persistence.jpa.entity.SongEntity;
import com.uistify.backend.infraestructure.persistence.jpa.entity.ArtistEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SongEntityMapper {

    SongEntityMapper INSTANCE = Mappers.getMapper(SongEntityMapper.class);

	@Mapping(source = "artist.id", target = "artistId")
    Song toDomain(SongEntity entity);

	@Mapping(source = "artistId", target = "artist", qualifiedByName = "artistIdToArtist")
    SongEntity toEntity(Song song);

	@Named("artistIdToArtist")
	public static ArtistEntity artistIdToArtist(Long artistId){
		if (artistId == null) return null;
		ArtistEntity artist = new ArtistEntity();
        artist.setId(artistId);
        return artist;
	}
}
