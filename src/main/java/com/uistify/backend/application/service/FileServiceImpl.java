package com.uistify.backend.application.service;

import com.uistify.backend.domain.exception.FileStorageException;
import com.uistify.backend.domain.model.FileDownload;
import com.uistify.backend.domain.port.in.FileUseCase;
import com.uistify.backend.domain.port.out.FileStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileUseCase {

    private final FileStorageRepository fileStorageRepository;

    @Override
    public Optional<FileDownload> getFile(String objectKey) {
        try {
            return fileStorageRepository.load(objectKey);
        } catch (FileStorageException e) {
            log.error("Error retrieving file {}: {}", objectKey, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public String uploadFile(byte[] file, String contentType) {
        return fileStorageRepository.uploadFile(file, contentType);
    }
}
