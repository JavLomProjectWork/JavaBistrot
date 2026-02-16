package com.javlom3.javabistrot.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private Integer numberOfGuests;

    @Column(nullable = false)
    private LocalDateTime bookingDateTime;

    @ManyToMany
    @JoinTable(
        name = "booking_waiters",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "waiter_id")
    )
    private List<User> assignedWaiters; 

    @Column(length = 500)
    private String notes;
}