package com.example.sportsmanagementsystem.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SportFieldDto(
        Long Id,
        String name,
        String sportType,
        String locationType,
        BigDecimal pricePerHour,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
