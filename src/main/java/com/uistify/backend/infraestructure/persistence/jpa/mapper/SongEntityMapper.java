package com.uistify.backend.infraestructure.persistence.jpa.mapper;

import com.uistify.backend.domain.model.Song;
import com.uistify.backend.infraestructure.persistence.jpa.entity.SongEntity;
import com.uistify.backend.infraestructure.persistence.jpa.entity.ArtistEntity;
import com.uistify.backend.infraestructure.persistence.jpa.entity.AlbumEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SongEntityMapper {

    SongEntityMapper INSTANCE = Mappers.getMapper(SongEntityMapper.class);

	@Mapping(source = "artist.id", target = "artistId")
	@Mapping(source = "album.id", target = "albumId")
    Song toDomain(SongEntity entity);

	@Mapping(source = "artistId", target = "artist", qualifiedByName = "artistIdToArtist")
	@Mapping(source = "albumId", target = "album", qualifiedByName = "albumIdToAlbum")
    SongEntity toEntity(Song song);

	@Named("artistIdToArtist")
	public static ArtistEntity artistIdToArtist(Long artistId){
		if (artistId == null) return null;
		ArtistEntity artist = new ArtistEntity();
        artist.setId(artistId);
        return artist;
	}

	@Named("albumIdToAlbum")
	public static AlbumEntity albumIdToAlbum(Long albumId){
		if (albumId == null) return null;
		AlbumEntity album = new AlbumEntity();
        album.setId(albumId);
        return album;
	}
}
