package com.uistify.backend.domain.port.in;

import com.uistify.backend.domain.model.FileDownload;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileUseCase {

    Optional<FileDownload> getFile(String objectKey);

    String uploadFile(byte[] file, String contentType);
}
