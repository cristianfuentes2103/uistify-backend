package com.uistify.backend.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.uistify.backend.domain.model.User;
import com.uistify.backend.presentation.rest.dto.ProfileDto;

@Mapper
public interface ProfileMapper {

	ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

	ProfileDto toDto(User user);
}
