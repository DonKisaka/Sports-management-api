package com.example.sportsmanagementsystem.service;

import com.example.sportsmanagementsystem.Dto.BookingRequest;
import com.example.sportsmanagementsystem.Dto.BookingResponse;
import com.example.sportsmanagementsystem.mapper.BookingMapper;
import com.example.sportsmanagementsystem.model.Booking;
import com.example.sportsmanagementsystem.model.BookingStatus;
import com.example.sportsmanagementsystem.model.SportField;
import com.example.sportsmanagementsystem.model.User;
import com.example.sportsmanagementsystem.repository.BookingRepository;
import com.example.sportsmanagementsystem.repository.SportRepository;
import com.example.sportsmanagementsystem.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final SportRepository sportRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, SportRepository sportRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.sportRepository = sportRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BookingResponse createBooking(BookingRequest dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        SportField sportField = sportRepository.findById(dto.sportFieldId())
                .orElseThrow(() -> new IllegalArgumentException("Sport field not found"));

        LocalTime start = dto.startTime();
        LocalTime end = dto.endTime();

        if (bookingRepository.hasOverlappingBooking(dto.sportFieldId(), start, end)) {
            throw new IllegalArgumentException("The requested time slot is already booked");
        }

        long hours = Duration.between(start, end).toHours();
        BigDecimal totalPrice = sportField.getPricePerHour().multiply(BigDecimal.valueOf(hours));

        Booking booking = bookingMapper.toEntity(dto);
        booking.setUser(currentUser);
        booking.setSportField(sportField);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toDto(savedBooking);
    }

    public BookingResponse getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(bookingMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public List<BookingResponse> getBookingsForField(Long fieldId) {
        return bookingMapper.toDtoList(bookingRepository.findBySportFieldId(fieldId));
    }

    public List<BookingResponse> getMyBookings() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<Booking> bookings = bookingRepository.findByUserId(currentUser.getId());
        return bookingMapper.toDtoList(bookings);
    }

    @Transactional
    public BookingResponse updateBooking(Long bookingId, BookingRequest dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Booking booking = bookingRepository.findByIdAndUserEmail(bookingId, username)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            booking.setStartTime(dto.startTime());
            booking.setEndTime(dto.endTime());

            long hours = Duration.between(dto.startTime(), dto.endTime()).toHours();
            BigDecimal totalPrice = booking.getSportField().getPricePerHour().multiply(BigDecimal.valueOf(hours));
            booking.setTotalPrice(totalPrice);
        }
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Booking booking = bookingRepository.findByIdAndUserEmail(bookingId, username)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CONFIRMED || booking.getStatus() == BookingStatus.PENDING) {
            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);
        } else {
            throw new IllegalArgumentException("Booking is not in CONFIRMED status");
        }
    }
}
