package com.javlom3.javabistrot.dto;

import java.time.LocalDateTime;
import java.util.List;

public record BookingDTO(
    Long id,
    String customerName,
    String email,
    String phoneNumber,
    Integer numberOfGuests,
    LocalDateTime bookingDateTime,
    List<Long> assignedWaiterIds,
    String notes 
) {}
