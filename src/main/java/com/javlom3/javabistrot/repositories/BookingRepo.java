package com.javlom3.javabistrot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javlom3.javabistrot.entities.Booking;
import java.util.List;
import java.time.LocalDateTime;


public interface BookingRepo extends JpaRepository<Booking, Long>{
    List<Booking> findByBookingDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Booking> findByCustomerNameContainingIgnoreCase(String customerName);

    List<Booking> findByPhoneNumber(String phoneNumber);

    List<Booking> findByEmail(String email);

    List<Booking> findByActiveTrue();
    List<Booking> findByActiveFalse();
}
