package com.uistify.backend.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder(toBuilder = true)
@Getter
public class Otp {

    private Long id;
    private String code;
    private Instant createdAt;
    private Long userId;

}
