package com.uistify.backend.service;

import org.springframework.http.ResponseEntity;

public interface IFileService {

    ResponseEntity<Object> getFile(String objectKey);

}
