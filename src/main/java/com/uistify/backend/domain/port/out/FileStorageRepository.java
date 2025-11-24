package com.uistify.backend.domain.port.out;

import com.uistify.backend.domain.model.FileDownload;

import java.util.Optional;

public interface FileStorageRepository {

    Optional<FileDownload> load(String objectKey);
    String uploadFile(byte[] file, String contentType);
}
