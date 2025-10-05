package com.uistify.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MariaDBContainer;

@Configuration
@Profile("test")
public class TestcontainersMariaDbConfig {

    @Bean
    @ServiceConnection  // Spring Boot 3.1+ cablea spring.datasource.* solo
    MariaDBContainer<?> mariadbContainer() {
        return new MariaDBContainer<>("mariadb:11.4")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
    }
}
