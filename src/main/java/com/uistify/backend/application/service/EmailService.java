package com.uistify.backend.application.service;

import com.uistify.backend.domain.exception.ServerErrorException;
import com.uistify.backend.domain.model.EmailRequest;
import com.uistify.backend.domain.port.in.EmailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class EmailService implements EmailUseCase {

    private final RestTemplateBuilder restTemplateBuilder;

    @Value("${email.api.key}")
    String emailApiKey;

    @Value("${email.api.url}")
    String emailApiUrl;

    @Override
    public void sendEmailHtml(String to, String subject, String body) throws ServerErrorException {

        RestTemplate restTemplate = restTemplateBuilder.build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("X_API_KEY", emailApiKey);

        EmailRequest emailRequest = EmailRequest.builder()
                .email(to)
                .subject(subject)
                .body(body)
                .build();

        HttpEntity<EmailRequest> requestEntity = new HttpEntity<>(emailRequest, headers);

        restTemplate.postForEntity(emailApiUrl, requestEntity, Void.class);
    }
}
