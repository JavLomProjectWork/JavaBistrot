package com.javlom3.javabistrot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javlom3.javabistrot.entities.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long>{

    
}
