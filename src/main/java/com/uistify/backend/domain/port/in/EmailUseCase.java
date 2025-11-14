package com.uistify.backend.domain.port.in;

public interface EmailUseCase {

    void sendEmailHtml(String to, String subject, String body) throws Exception;

}
