package com.example.sportsmanagementsystem.Dto;

import com.example.sportsmanagementsystem.model.LocationType;
import com.example.sportsmanagementsystem.model.SportType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SportFieldResponse(
        Long Id,
        String name,
        SportType sportType,
        LocationType locationType,
        BigDecimal pricePerHour,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}
