package com.uistify.backend.presentation.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

    int status;
    String message;

}
