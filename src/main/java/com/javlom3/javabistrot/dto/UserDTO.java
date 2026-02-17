package com.javlom3.javabistrot.dto;

import java.util.List;

import com.javlom3.javabistrot.entities.Booking;
import com.javlom3.javabistrot.entities.Role;

public record UserDTO(
    Long id,
    String username,
    String password,
    Role role,
    List<Booking> bookings
) {}
