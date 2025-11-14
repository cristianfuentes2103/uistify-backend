package com.uistify.backend.domain.port.in;

import com.uistify.backend.domain.model.Otp;

public interface OtpUseCase {

    boolean isOtpValid(Otp otp);

    Otp saveOtp(Long userId);

    Otp findByCode(String code);

    void deleteOtp(Otp otp);

}
