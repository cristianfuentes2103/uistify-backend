package com.uistify.backend.domain.port.out;

import com.uistify.backend.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User save(User user);

    User findById(Long userId);
}
