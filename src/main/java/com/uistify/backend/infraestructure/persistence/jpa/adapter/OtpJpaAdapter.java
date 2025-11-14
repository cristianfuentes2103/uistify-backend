package com.uistify.backend.infraestructure.persistence.jpa.adapter;

import com.uistify.backend.domain.model.Otp;
import com.uistify.backend.domain.port.out.OtpRepository;
import com.uistify.backend.infraestructure.persistence.jpa.mapper.OtpMapper;
import com.uistify.backend.infraestructure.persistence.jpa.repository.OtpJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class OtpJpaAdapter implements OtpRepository {

    private final OtpJpaRepository otpJpaRepository;

    @Override
    public Otp save(Otp otp) {
        return OtpMapper.INSTANCE.toOtp(otpJpaRepository
                .save(OtpMapper.INSTANCE.toOtpEntity(otp)));
    }

    @Override
    public Otp findByCode(String code) {
        return otpJpaRepository.findByCode(code)
                .map(OtpMapper.INSTANCE::toOtp)
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        otpJpaRepository.deleteById(id);
    }

}
