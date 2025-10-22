package com.uistify.backend.infraestructure.persistence.jpa.mapper;

import com.uistify.backend.domain.model.User;
import com.uistify.backend.infraestructure.persistence.jpa.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    User toDomain(UserEntity entity);

    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "playlists", ignore = true)
    UserEntity toEntity(User user);
}
