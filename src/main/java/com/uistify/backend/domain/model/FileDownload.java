package com.uistify.backend.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FileDownload {

    byte[] content;
    String filename;
    String contentType;
}

