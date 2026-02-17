package com.javlom3.javabistrot.mapper;

import com.javlom3.javabistrot.dto.BookingDTO;
import com.javlom3.javabistrot.entities.Booking;

public class BookingMapper {
    public Booking toEntity(BookingDTO dto) {
        Booking entity = new Booking();
        entity.setCustomerName(dto.customerName());
        entity.setEmail(dto.email());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setNumberOfGuests(dto.numberOfGuests());
        entity.setBookingDateTime(dto.bookingDateTime());
        entity.setNotes(dto.notes());
        return entity;
    }
    
    public BookingDTO toDto(Booking entity) {
        return new BookingDTO(
            entity.getId(),
            entity.getCustomerName(),
            entity.getEmail(),
            entity.getPhoneNumber(),
            entity.getNumberOfGuests(),
            entity.getBookingDateTime(),
            entity.getAssignedWaiters(),
            entity.getNotes()
        );
    }
}
