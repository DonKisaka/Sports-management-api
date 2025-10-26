package com.example.sportsmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Sport field is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_field_id")
    private SportField sportField;

    @NotBlank(message = "Client name is required")
    @Column(nullable = false, name = "client_name")
    private String clientName;

    @NotBlank(message = "Client phone is required")
    @Column(nullable = false, name = "client_phone")
    private String clientPhone;

    @NotBlank(message = "Client email is required")
    @Email(message = "Client email must be valid")
    @Column(nullable = false, name = "client_email")
    private String clientEmail;

    @NotNull(message = "Start date and time is required")
    @Column(nullable = false, name = "start_date_time")
    private LocalDateTime startDateTime;

    @NotNull(message = "End date and  time is required")
    @Column(nullable = false, name = "end_date_time")
    private LocalDateTime endDateTime;

    @NotNull(message = "Total price is required")
    @Column(nullable = false, precision = 10, scale = 2, name = "total_price")
    private BigDecimal totalPrice;

    @NotNull(message = "Booking status is required")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(precision = 5, scale = 2)
    private BigDecimal temperature;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
