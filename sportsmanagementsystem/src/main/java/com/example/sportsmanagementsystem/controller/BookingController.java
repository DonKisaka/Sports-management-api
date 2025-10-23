package com.example.sportsmanagementsystem.controller;

import com.example.sportsmanagementsystem.Dto.BookingRequest;
import com.example.sportsmanagementsystem.Dto.BookingResponse;
import com.example.sportsmanagementsystem.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest dto
    ) {
        BookingResponse response = bookingService.createBooking(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id
    ) {
        BookingResponse response = bookingService.getBookingById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<BookingResponse>> getBookingsForUser(
            @PathVariable Long id
    ) {
        List<BookingResponse> response = bookingService.getMyBookings();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/field/{id}")
    public ResponseEntity<List<BookingResponse>> getBookingsForField(
            @PathVariable Long id
    ) {
        List<BookingResponse> response = bookingService.getBookingsForField(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody BookingRequest dto
    ) {
        BookingResponse response = bookingService.updateBooking(id, dto);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(
            @PathVariable Long id
    ) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }
}
