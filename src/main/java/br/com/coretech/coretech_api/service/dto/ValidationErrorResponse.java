package br.com.coretech.coretech_api.service.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String error,
        Map<String, String> fields
) {}