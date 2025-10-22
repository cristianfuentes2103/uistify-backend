package com.uistify.backend.domain.port.in;

import com.uistify.backend.domain.model.User;

import java.util.Optional;

public interface UserUseCase {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User register(User user);

    boolean isPasswordValid(String rawPassword, String encodedPassword);
}

