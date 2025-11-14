package com.uistify.backend.domain.port.in;

import com.uistify.backend.domain.exception.ServerErrorException;

public interface EmailUseCase {

    void sendEmailHtml(String to, String subject, String body) throws ServerErrorException;

}
