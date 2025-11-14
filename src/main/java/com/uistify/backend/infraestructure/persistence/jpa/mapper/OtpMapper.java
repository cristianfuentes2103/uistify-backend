package com.uistify.backend.infraestructure.persistence.jpa.mapper;

import com.uistify.backend.domain.model.Otp;
import com.uistify.backend.infraestructure.persistence.jpa.entity.OtpEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OtpMapper {

    OtpMapper INSTANCE = Mappers.getMapper(OtpMapper.class);

    Otp toOtp(OtpEntity otpEntity);

    OtpEntity toOtpEntity(Otp otp);

}
