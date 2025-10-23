package com.example.sportsmanagementsystem.repository;

import com.example.sportsmanagementsystem.model.Booking;
import com.example.sportsmanagementsystem.model.BookingStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findBySportFieldId(Long sportFieldId);


    boolean hasOverlappingBooking(@NotNull(message = "Sport field id must not be null") Long aLong, LocalTime start, LocalTime end);
}
