package com.javlom3.javabistrot.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.javlom3.javabistrot.dto.BookingDTO;
import com.javlom3.javabistrot.entities.Booking;
import com.javlom3.javabistrot.entities.User;
import com.javlom3.javabistrot.mapper.BookingMapper;
import com.javlom3.javabistrot.repositories.BookingRepo;
import com.javlom3.javabistrot.repositories.UserRepo;

import jakarta.transaction.Transactional;

@Service
@Slf4j
public class BookingService {
	private final BookingRepo bookingRepo;
	private final UserRepo userRepo;
	private final BookingMapper bookingMapper;

	public BookingService(BookingRepo bookingRepo, UserRepo userRepo, BookingMapper bookingMapper) {
		this.bookingRepo = bookingRepo;
		this.userRepo = userRepo;
		this.bookingMapper = bookingMapper;
	}

	public static final int MAX_DAYS_IN_FUTURE = 90;
	public static final java.time.LocalTime LUNCH_START = java.time.LocalTime.of(12, 0);
	public static final java.time.LocalTime LUNCH_END = java.time.LocalTime.of(14, 0);
	public static final java.time.LocalTime DINNER_START = java.time.LocalTime.of(19, 0);
	public static final java.time.LocalTime DINNER_END = java.time.LocalTime.of(21, 0);

	@Transactional
	public Optional<BookingDTO> toggleActive(Long id) {
		log.info("toggleActive called for booking id={}", id);
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


	@Transactional
	public Optional<BookingDTO> createBooking(BookingDTO dto) {
		log.info("createBooking called with dto={}", dto);
		// Restrizione orario: solo tra 12-14 e 19-21
		if (dto.bookingDateTime() != null) {
			LocalTime time = dto.bookingDateTime().toLocalTime();
			boolean valid = (time.compareTo(LUNCH_START) >= 0 && time.compareTo(LUNCH_END) <= 0) ||
						   (time.compareTo(DINNER_START) >= 0 && time.compareTo(DINNER_END) <= 0);
			if (!valid) {
				throw new IllegalArgumentException("L'orario di prenotazione deve essere tra le " + LUNCH_START + "-" + LUNCH_END + " o tra le " + DINNER_START + "-" + DINNER_END + ".");
			}
			LocalDate date = dto.bookingDateTime().toLocalDate();
			valid = (!date.isBefore(LocalDate.now()) && !date.isAfter(LocalDate.now().plusDays(MAX_DAYS_IN_FUTURE)));
            
            if (!valid) {
                throw new IllegalArgumentException("La data di prenotazione non può essere nel passato e deve essere entro " + MAX_DAYS_IN_FUTURE + " giorni da oggi.");}
			}
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
		log.info("updateBooking called for id={} with dto={}", id, dto);
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
		log.info("addWaiter called for bookingId={} waiterId={}", bookingId, waiterId);
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
		log.info("removeWaiter called for bookingId={} waiterId={}", bookingId, waiterId);
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
		log.info("deleteBooking called for id={}", id);
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
	
	public Boolean existsById(Long id){
		return bookingRepo.existsById(id);
	}
}
