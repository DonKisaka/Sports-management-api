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

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long bookingId
    ) {
        BookingResponse response = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {
        List<BookingResponse> response = bookingService.getMyBookings();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/field/{fieldId}")
    public ResponseEntity<List<BookingResponse>> getBookingsForField(
            @PathVariable Long fieldId
    ) {
        List<BookingResponse> response = bookingService.getBookingsForField(fieldId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> updateBooking(
            @PathVariable Long bookingId,
            @Valid @RequestBody BookingRequest dto
    ) {
        BookingResponse response = bookingService.updateBooking(bookingId, dto);
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
