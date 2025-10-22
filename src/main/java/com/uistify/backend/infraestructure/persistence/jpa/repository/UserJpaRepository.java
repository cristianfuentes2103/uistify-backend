package com.uistify.backend.infraestructure.persistence.jpa.repository;

import com.uistify.backend.infraestructure.persistence.jpa.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);
	Boolean existsByEmail(String email);
}
