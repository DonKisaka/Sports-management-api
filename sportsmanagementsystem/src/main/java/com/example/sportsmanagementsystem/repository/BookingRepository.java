package com.example.sportsmanagementsystem.repository;

import com.example.sportsmanagementsystem.model.Booking;
import com.example.sportsmanagementsystem.model.BookingStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findBySportFieldId(Long sportFieldId);
    Optional<Booking> findByIdAndUserEmail(Long id, String email);


    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
           "FROM Booking b " +
            "WHERE b.sportField.id = :sportFieldId AND " +
            "((b.startDateTime < :endDateTime AND b.endDateTime > :startDateTime))")
    boolean hasOverlappingBooking(
            @Param("sportFieldId") Long sportFieldId,
            @Param("startDateTime")LocalDateTime startDateTime,
            @Param("endDateTime")LocalDateTime endDateTime);
}
