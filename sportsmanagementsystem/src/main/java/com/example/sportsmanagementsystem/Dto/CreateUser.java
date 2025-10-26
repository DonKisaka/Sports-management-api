package com.example.sportsmanagementsystem.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUser(
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be valid")
   String email,
   @NotBlank(message = "Username must not be blank")
   String username,
   @NotBlank(message = "Password must not be blank")
   String password
) {}
