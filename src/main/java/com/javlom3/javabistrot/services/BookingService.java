package com.javlom3.javabistrot.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.javlom3.javabistrot.dto.BookingDTO;
import com.javlom3.javabistrot.entities.Booking;
import com.javlom3.javabistrot.entities.User;
import com.javlom3.javabistrot.mapper.BookingMapper;
import com.javlom3.javabistrot.repositories.BookingRepo;
import com.javlom3.javabistrot.repositories.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

	@Transactional
	public Optional<BookingDTO> toggleActive(Long id) {
		return bookingRepo.findById(id).map(booking -> {
			booking.setActive(booking.getActive() == null ? Boolean.TRUE : !booking.getActive());
			Booking updated = bookingRepo.save(booking);
			return bookingMapper.toDto(updated);
		});
	}

	@Transactional
	public List<BookingDTO> findActive() {
		return bookingRepo.findByActiveTrue().stream().map(bookingMapper::toDto).toList();
	}

	@Transactional
	public List<BookingDTO> findNotActive() {
		return bookingRepo.findByActiveFalse().stream().map(bookingMapper::toDto).toList();
	}

	private final BookingRepo bookingRepo;
	private final UserRepo userRepo;
	private final BookingMapper bookingMapper;

	public BookingService(BookingRepo bookingRepo, UserRepo userRepo, BookingMapper bookingMapper) {
		this.bookingRepo = bookingRepo;
		this.userRepo = userRepo;
		this.bookingMapper = bookingMapper;
	}

	@Transactional
	public Optional<BookingDTO> createBooking(BookingDTO dto) {
		Booking entity = bookingMapper.toEntity(dto);
		entity.setAssignedWaiters(resolveWaiters(dto.assignedWaiterIds()));
		Booking saved = bookingRepo.save(entity);
		return Optional.of(bookingMapper.toDto(saved));
	}

	@Transactional
	public Optional<BookingDTO> getBookingById(Long id) {
		return bookingRepo.findById(id).map(bookingMapper::toDto);
	}

	@Transactional
	public List<BookingDTO> getAllBookings() {
		return bookingRepo.findAll().stream().map(bookingMapper::toDto).toList();
	}

	@Transactional
	public Optional<BookingDTO> updateBooking(Long id, BookingDTO dto) {
		return bookingRepo.findById(id).map(existing -> {
			if (dto.customerName() != null) {
				existing.setCustomerName(dto.customerName());
			}
			if (dto.email() != null) {
				existing.setEmail(dto.email());
			}
			if (dto.phoneNumber() != null) {
				existing.setPhoneNumber(dto.phoneNumber());
			}
			if (dto.numberOfGuests() != null) {
				existing.setNumberOfGuests(dto.numberOfGuests());
			}
			if (dto.bookingDateTime() != null) {
				existing.setBookingDateTime(dto.bookingDateTime());
			}
			if (dto.notes() != null) {
				existing.setNotes(dto.notes());
			}
			if (dto.active() != null) {
				existing.setActive(dto.active());
			}
			if (dto.assignedWaiterIds() != null) {
				existing.setAssignedWaiters(resolveWaiters(dto.assignedWaiterIds()));
			}
			Booking updated = bookingRepo.save(existing);
			return bookingMapper.toDto(updated);
		});
	}

	@Transactional
	public Optional<BookingDTO> addWaiter(Long bookingId, Long waiterId) {
		if (waiterId == null) {
			return bookingRepo.findById(bookingId).map(bookingMapper::toDto);
		}
		return bookingRepo.findById(bookingId).map(existing -> {
			Set<User> current = existing.getAssignedWaiters();
			if (current == null) {
				current = new HashSet<>();
			}
			userRepo.findById(waiterId).ifPresent(current::add);
			existing.setAssignedWaiters(current);
			Booking updated = bookingRepo.save(existing);
			return bookingMapper.toDto(updated);
		});
	}

	@Transactional
	public Optional<BookingDTO> removeWaiter(Long bookingId, Long waiterId) {
		if (waiterId == null) {
			return bookingRepo.findById(bookingId).map(bookingMapper::toDto);
		}
		return bookingRepo.findById(bookingId).map(existing -> {
			Set<User> current = existing.getAssignedWaiters();
			if (current == null || current.isEmpty()) {
				existing.setAssignedWaiters(Set.of());
			} else {
				current.removeIf(user -> user.getId() != null && user.getId().equals(waiterId));
				existing.setAssignedWaiters(current);
			}
			Booking updated = bookingRepo.save(existing);
			return bookingMapper.toDto(updated);
		});
	}

	@Transactional
	public void deleteBooking(Long id) {
		if (!bookingRepo.existsById(id)) {
            throw new IllegalArgumentException("Prenotazione con id " + id + " non trovata");
        }
        bookingRepo.deleteById(id);
	}

	private Set<User> resolveWaiters(Set<Long> waiterIds) {
		if (waiterIds == null || waiterIds.isEmpty()) {
			return Set.of();
		}
		return new HashSet<>(userRepo.findAllById(waiterIds));
	}
    
    public List<BookingDTO> getByDay(LocalDate date) {
        return bookingRepo.findByBookingDateTimeBetween(date.atStartOfDay(), date.atTime(LocalTime.MAX))
            .stream()
            .map(bookingMapper::toDto)
            .toList();
    }

    public int countGuestsByDay(LocalDate date) {
        return bookingRepo.findByBookingDateTimeBetween(date.atStartOfDay(), date.atTime(LocalTime.MAX))
            .stream()
            .mapToInt(Booking::getNumberOfGuests)
            .sum();
    }
    
    public List<BookingDTO> getByCustomerName(String name) {
        return bookingRepo.findByCustomerNameContainingIgnoreCase(name)
            .stream()
            .map(bookingMapper::toDto)
            .toList();
    }

    public List<BookingDTO> getByPhoneNumber(String phone) {
        return bookingRepo.findByPhoneNumber(phone)
            .stream()
            .map(bookingMapper::toDto)
            .toList();
    }

    public List<BookingDTO> getByEmail(String email) {
        return bookingRepo.findByEmail(email)
            .stream()
            .map(bookingMapper::toDto)
            .toList();
    }
}
