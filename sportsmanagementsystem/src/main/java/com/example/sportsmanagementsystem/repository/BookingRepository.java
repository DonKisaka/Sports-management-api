package com.example.sportsmanagementsystem.repository;

import com.example.sportsmanagementsystem.model.Booking;
import com.example.sportsmanagementsystem.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByStatus(BookingStatus status);

}
