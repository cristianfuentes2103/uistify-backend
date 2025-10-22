package com.uistify.backend.infraestructure.persistence.jpa.adapter;

import com.uistify.backend.domain.model.User;
import com.uistify.backend.domain.port.out.UserRepository;
import com.uistify.backend.infraestructure.persistence.jpa.mapper.UserEntityMapper;
import com.uistify.backend.infraestructure.persistence.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepository {

    private static final UserEntityMapper USER_ENTITY_MAPPER = UserEntityMapper.INSTANCE;

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(USER_ENTITY_MAPPER::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return USER_ENTITY_MAPPER.toDomain(userJpaRepository.save(USER_ENTITY_MAPPER.toEntity(user)));
    }
}
