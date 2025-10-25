package com.example.sportsmanagementsystem.Dto;

import com.example.sportsmanagementsystem.model.LocationType;
import com.example.sportsmanagementsystem.model.SportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SportFieldRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotNull(message = "Sport type cannot be blank")
        SportType sportType,

        @NotNull(message = "Location type cannot be blank")
        LocationType locationType,

        @NotNull(message = "Price per hour cannot be null")
        @Positive(message = "Price per hour must be positive")
        BigDecimal pricePerHour
) {}
