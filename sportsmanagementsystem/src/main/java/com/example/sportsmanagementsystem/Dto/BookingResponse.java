package com.example.sportsmanagementsystem.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingResponse(
        Long id,
        Long sportFieldId,
        String sportFieldName,
        String clientName,
        String clientPhone,
        String clientEmail,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        BigDecimal totalPrice,
        String status,
        BigDecimal temperature,
        LocalDateTime createdAt
) {}
