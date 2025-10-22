package com.uistify.backend.service.impl;

import com.uistify.backend.presentation.dto.ErrorDto;
import com.uistify.backend.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.net.URI;

@Service
@Slf4j
public class FileServiceImpl implements IFileService {

    @Value("${bucket.name}")
    String bucketName;

    @Value("${access.key}")
    String accessKey;

    @Value("${secret.key}")
    String secretKey;

    @Value("${region.key}")
    String region;

    @Value("${url.minio}")
    String url;

    @Override
    public ResponseEntity<Object> getFile(String objectKey) {
        try {

            AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

            S3Client s3Client = S3Client
                    .builder()
                    .region(Region.of(region))
                    .endpointOverride(URI.create(url))
                    .credentialsProvider(StaticCredentialsProvider.create(credentials))
                    .build();


            ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(GetObjectRequest.builder()
                    .bucket(bucketName).key(objectKey)
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
