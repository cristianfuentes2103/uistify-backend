package com.uistify.backend.infraestructure.persistence.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.uistify.backend.domain.model.Artist;
import com.uistify.backend.infraestructure.persistence.jpa.entity.ArtistEntity;
import com.uistify.backend.infraestructure.persistence.jpa.entity.UserEntity;

@Mapper
public interface ArtistEntityMapper {

	ArtistEntityMapper INSTANCE = Mappers.getMapper(ArtistEntityMapper.class);

	@Mapping(target = "userId", source = "user.id")
	Artist toDomain(ArtistEntity entity);

	@Mapping(target = "user", source = "userId", qualifiedByName = "userIdToUser")
	ArtistEntity toEntity(Artist artist);

	@Named("userIdToUser")
	public static UserEntity userIdToUser(Long id){
		if (id == null) return null;
		UserEntity user = new UserEntity();
		user.setId(id);
		return user;
	}
}
