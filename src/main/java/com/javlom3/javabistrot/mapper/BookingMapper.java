package com.javlom3.javabistrot.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.javlom3.javabistrot.dto.BookingDTO;
import com.javlom3.javabistrot.entities.Booking;
import com.javlom3.javabistrot.entities.User;

@Component
public class BookingMapper {
    public Booking toEntity(BookingDTO dto) {
        Booking entity = new Booking();
        entity.setCustomerName(dto.customerName());
        entity.setEmail(dto.email());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setNumberOfGuests(dto.numberOfGuests());
        entity.setBookingDateTime(dto.bookingDateTime());
        entity.setNotes(dto.notes());
        entity.setActive(dto.active());
        return entity;
    }
    
    public BookingDTO toDto(Booking entity) {
        Set<Long> assignedWaiterIds = null;
        if (entity.getAssignedWaiters() != null) {
            assignedWaiterIds = entity.getAssignedWaiters()
                .stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        }

        return new BookingDTO(
            entity.getId(),
            entity.getCustomerName(),
            entity.getEmail(),
            entity.getPhoneNumber(),
            entity.getNumberOfGuests(),
            entity.getBookingDateTime(),
            assignedWaiterIds,
            entity.getNotes(),
            entity.getActive()
        );
    }
}
