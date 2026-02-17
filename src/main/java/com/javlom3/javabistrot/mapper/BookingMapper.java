package com.javlom3.javabistrot.mapper;

import com.javlom3.javabistrot.dto.BookingDTO;
import com.javlom3.javabistrot.entities.Booking;
import com.javlom3.javabistrot.entities.User;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Long> assignedWaiterIds = null;
        if (entity.getAssignedWaiters() != null) {
            assignedWaiterIds = entity.getAssignedWaiters()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        }

        return new BookingDTO(
            entity.getId(),
            entity.getCustomerName(),
            entity.getEmail(),
            entity.getPhoneNumber(),
            entity.getNumberOfGuests(),
            entity.getBookingDateTime(),
            assignedWaiterIds,
            entity.getNotes()
        );
    }
}
