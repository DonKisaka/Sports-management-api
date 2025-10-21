package com.example.sportsmanagementsystem.service;

import com.example.sportsmanagementsystem.Dto.AuthenticationResponse;
import com.example.sportsmanagementsystem.Dto.CreateUser;
import com.example.sportsmanagementsystem.Dto.LoginUser;
import com.example.sportsmanagementsystem.config.JwtService;
import com.example.sportsmanagementsystem.model.User;
import com.example.sportsmanagementsystem.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse signUp(CreateUser input) {
        if (userRepository.findByEmail(input.email()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = User.builder()
                .email(input.email())
                .username(input.username())
                .password(passwordEncoder.encode(input.password()))
                .build();
        user.setEnabled(true);
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(
                jwtToken,
                user.getEmail(),
                user.getUsername()
        );
    }

    public AuthenticationResponse authenticate(LoginUser input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        User user = userRepository.findByEmail(input.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(
                jwtToken,
                user.getEmail(),
                user.getUsername()
        );

    }
}


