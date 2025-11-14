package com.uistify.backend.domain.port.out;

import com.uistify.backend.domain.model.Otp;

public interface OtpRepository {

    Otp save(Otp otp);

    Otp findByCode(String code);

    void deleteById(Long id);

}
