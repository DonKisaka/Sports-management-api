package com.example.sportsmanagementsystem.Dto;

public record AuthenticationResponse(
        String token,
        String email,
        String username
) {}
