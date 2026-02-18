package com.javlom3.javabistrot.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javlom3.javabistrot.dto.BookingDTO;
import com.javlom3.javabistrot.services.BookingService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PublicController {

    private final BookingService bookingService;

    public PublicController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "public/home";
    }

    @GetMapping("/menu")
    public String menu() {
        return "public/menu";
    }

    @GetMapping("/prenota")
    public String prenotaForm() {
        return "public/prenota";
    }

    @PostMapping("/prenota")
    public String prenotaSubmit(
            @RequestParam String customerName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam Integer numberOfGuests,
            @RequestParam String bookingDate,
            @RequestParam String bookingTime,
            @RequestParam(required = false) String notes,
            RedirectAttributes redirectAttributes) {
        try {
            LocalDate date = LocalDate.parse(bookingDate);
            LocalTime time = LocalTime.parse(bookingTime);
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            BookingDTO dto = new BookingDTO(null, customerName, email, phoneNumber, numberOfGuests, dateTime, null, notes);
            Optional<BookingDTO> newBooking = bookingService.createBooking(dto);
            if (newBooking.isPresent()) {
                log.info("Nuova prenotazione pubblica creata: {}", newBooking.get());
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Prenotazione confermata! Ti aspettiamo il " + dateTime.toLocalDate() + " alle " + dateTime.toLocalTime());
            }
        } catch (ConstraintViolationException e) {
            String errorMessages = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
            log.error("Errore validazione prenotazione: {}", errorMessages);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
        } catch (TransactionSystemException e) {
            Throwable cause = e.getRootCause();
            if (cause instanceof ConstraintViolationException cve) {
                String errorMessages = cve.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
                log.error("Errore validazione prenotazione: {}", errorMessages);
                redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
            } else {
                log.error("Errore transazione: {}", e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Errore nella prenotazione. Riprova.");
            }
        } catch (Exception e) {
            log.error("Errore creazione prenotazione: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Errore nella prenotazione. Riprova.");
        }
        return "redirect:/prenota";
    }
}
