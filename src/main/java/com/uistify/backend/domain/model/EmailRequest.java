package com.uistify.backend.domain.model;


import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class EmailRequest {

    private String email;
    private String subject;
    private String body;

}
