package com.example.sportsmanagementsystem.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookingRequest(
        @NotNull(message = "Sport field id must not be null")
        Long sportFieldId,

        @NotBlank(message = "Client name must not be null")
        String clientName,

        @NotBlank(message = "Client phone must not be null")
        String clientPhone,

        @NotBlank(message = "Client email must not be null")
        @Email(message = "Client email must be valid")
        String clientEmail,

        @NotNull(message = "Booking date must not be null")
        LocalDateTime startDateTime,

        @NotNull(message = "End date time must not be null")
        LocalDateTime endDateTime
) {}
