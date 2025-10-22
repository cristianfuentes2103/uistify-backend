package com.uistify.backend.domain.port.in;

import org.springframework.http.ResponseEntity;

public interface FileUseCase {

    ResponseEntity<Object> getFile(String objectKey);

}
