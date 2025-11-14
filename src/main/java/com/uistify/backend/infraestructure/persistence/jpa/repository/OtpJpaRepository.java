package com.uistify.backend.infraestructure.persistence.jpa.repository;

import com.uistify.backend.infraestructure.persistence.jpa.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpJpaRepository extends JpaRepository<OtpEntity, Long> {

    Optional<OtpEntity> findByCode(String code);

}
