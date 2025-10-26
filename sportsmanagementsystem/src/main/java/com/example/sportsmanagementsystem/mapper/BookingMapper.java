package com.example.sportsmanagementsystem.mapper;

import com.example.sportsmanagementsystem.Dto.BookingRequest;
import com.example.sportsmanagementsystem.Dto.BookingResponse;
import com.example.sportsmanagementsystem.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "sportField", ignore = true)
    Booking toEntity(BookingRequest bookingRequest);

    @Mapping(source = "sportField.name", target= "sportFieldName")
    @Mapping(source = "status", target = "status")
    BookingResponse toDto(Booking booking);

    List<BookingResponse> toDtoList(List<Booking> bookings);

}
