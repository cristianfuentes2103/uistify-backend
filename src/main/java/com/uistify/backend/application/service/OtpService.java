package com.uistify.backend.application.service;

import com.uistify.backend.domain.model.Otp;
import com.uistify.backend.domain.port.in.OtpUseCase;
import com.uistify.backend.domain.port.out.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpService implements OtpUseCase {

    private final OtpRepository otpRepository;

    @Override
    public boolean isOtpValid(Otp otp) {
        return Instant.now().isBefore(otp.getCreatedAt().plus(5L, ChronoUnit.MINUTES));
    }

    @Override
    public Otp saveOtp(Long userId) {
        Otp otp = Otp.builder()
                .code(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .userId(userId)
                .build();
        return otpRepository.save(otp);
    }

    @Override
    public Otp findByCode(String code) {
        return otpRepository.findByCode(code);
    }

    @Override
    public void deleteOtp(Otp otp) {
        otpRepository.deleteById(otp.getId());
    }
}
