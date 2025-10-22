package com.example.sportsmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sport_fields")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SportField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull(message = "Sport type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "sport_type")
    private SportType sportType;

    @NotNull(message = "Location type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "location_type")
    private LocationType locationType;

    @NotNull(message = "Price per hour cannot be null")
    @Positive(message = "Price per hour must be positive")
    @Column(nullable = false, precision = 10, scale = 2, name = "price_per_hour")
    private BigDecimal pricePerHour;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean isOutdoor() {
        return LocationType.OUTDOOR.equals(locationType);
    }

    public boolean isIndoor() {
        return LocationType.INDOOR.equals(locationType);
    }
}
