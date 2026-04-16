package com.example.toolschallenge.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String message,
        List<String> details
) {}