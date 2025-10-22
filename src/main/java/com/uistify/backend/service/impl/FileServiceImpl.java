package com.uistify.backend.service.impl;

import com.uistify.backend.presentation.dto.ErrorDto;
import com.uistify.backend.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Service
@Slf4j
public class FileServiceImpl implements IFileService {

    @Value("${bucket.name}")
    String bucketName;

    @Autowired
    S3Client s3Client;

    @Override
    public ResponseEntity<Object> getFile(String objectKey) {
        try {
            ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build()
            );
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + objectKey);
            headers.add("Content-Type", responseInputStream.response().contentType());
            return new ResponseEntity<>(responseInputStream.readAllBytes(), headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(ErrorDto.builder().status(500).message("Error obteniendo object").build(), HttpStatusCode.valueOf(500));
        }
    }

}
