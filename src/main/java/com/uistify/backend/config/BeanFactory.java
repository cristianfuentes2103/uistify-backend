package com.uistify.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

@Configuration
@Slf4j
public class BeanFactory {

    @Bean
    public S3Client s3Client(@Value("${access.key}") String accessKey,
                             @Value("${secret.key}") String secretKey,
                             @Value("${region.key}") String region,
                             @Value("${url.minio}") String url) {
        AwsCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        return S3Client
                .builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(url))
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build())
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

}
