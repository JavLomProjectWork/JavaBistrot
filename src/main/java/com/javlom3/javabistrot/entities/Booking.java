package com.javlom3.javabistrot.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

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

    @NotBlank(message = "Customer name required.")
    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    @Email(message = "Insert valid email address.")
    private String email;

    @Pattern(regexp = "^\\+?[0-9.]{7,15}$", message = "Invalid phone number")
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Min(value = 1, message = "There must be at least one guest.")
    @Max(value = 20, message = "Can't insert booking with more than 20 guests.")
    @Column(nullable = false)
    private Integer numberOfGuests;

    @Column(nullable = false)
    @Future(message = "Can't insert expired booking.")
    private LocalDateTime bookingDateTime;

    @ManyToMany
    @JoinTable(
        name = "booking_waiters",
        joinColumns = @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)),
        inverseJoinColumns = @JoinColumn(name = "waiter_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    )
    private Set<User> assignedWaiters; 

    @Column(length = 500)
    private String notes;
    
    @Column(nullable = false)
    private Boolean active;
}