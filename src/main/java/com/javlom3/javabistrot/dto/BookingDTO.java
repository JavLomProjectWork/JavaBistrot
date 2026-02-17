package com.javlom3.javabistrot.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.javlom3.javabistrot.entities.User;

public record BookingDTO(
    Long id,
    String customerName,
    String email,
    String phoneNumber,
    Integer numberOfGuests,
    LocalDateTime bookingDateTime,
    List<User> assignedWaiters,
    String notes 
) {}
