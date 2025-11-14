package com.uistify.backend.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class User {

    Long id;
    String name;
    String email;
    String password;
    Integer verified;

}

