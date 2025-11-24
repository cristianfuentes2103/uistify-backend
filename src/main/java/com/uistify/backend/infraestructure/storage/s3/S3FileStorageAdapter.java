package com.uistify.backend.infraestructure.storage.s3;

import com.uistify.backend.domain.exception.FileStorageException;
import com.uistify.backend.domain.model.FileDownload;
import com.uistify.backend.domain.port.out.FileStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3FileStorageAdapter implements FileStorageRepository {

    private final S3Client s3Client;

    @Value("${bucket.name}")
    private String bucketName;

    @Override
    public Optional<FileDownload> load(String objectKey) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        try (ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request)) {
            byte[] content = response.readAllBytes();
            String contentType = response.response().contentType();
            return Optional.of(FileDownload.builder()
                    .content(content)
                    .contentType(contentType == null ? "application/octet-stream" : contentType)
                    .filename(objectKey)
                    .build());
        } catch (NoSuchKeyException e) {
            log.warn("File not found in S3. bucket={}, key={}", bucketName, objectKey);
            return Optional.empty();
        } catch (S3Exception | IOException e) {
            throw new FileStorageException("Error retrieving file from storage", e);
        }
    }

    @Override
    public String uploadFile(byte[] file, String contentType) {
        String key = UUID.randomUUID().toString();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();

        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file));
            log.info("Archivo subido exitosamente con key: {}", key);
            return key;
        } catch (S3Exception e) {
            log.error(e.getMessage());
            throw new FileStorageException("Error subiendo archivo a S3");
        }
    }

}
