package br.com.coretech.coretech_api.service.dto;

import java.time.LocalDateTime;


public record ErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message

) {}