package com.javlom3.javabistrot.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javlom3.javabistrot.dto.BookingDTO;
import com.javlom3.javabistrot.services.BookingService;
import com.javlom3.javabistrot.services.UserService;

@Controller
@RequestMapping("/bookings")
public class BookingManageController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingManageController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("/manage")
    public String manageBookings(
            @RequestParam(required = false) String date,
            Model model,
            Authentication authentication) {
        LocalDate selectedDate = (date != null && !date.isEmpty())
            ? LocalDate.parse(date)
            : LocalDate.now();

        // Recupera tutte le prenotazioni del giorno e separa attive/non attive
        List<BookingDTO> allBookings = bookingService.getByDay(selectedDate);
        List<BookingDTO> activeBookings = allBookings.stream().filter(b -> Boolean.TRUE.equals(b.active())).toList();
        List<BookingDTO> notActiveBookings = allBookings.stream().filter(b -> !Boolean.TRUE.equals(b.active())).toList();
        int totalGuests = activeBookings.stream().mapToInt(b -> b.numberOfGuests() != null ? b.numberOfGuests() : 0).sum();

        boolean isMaitre = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_MAITRE"));

        model.addAttribute("activeBookings", activeBookings);
        model.addAttribute("notActiveBookings", notActiveBookings);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("totalGuests", totalGuests);
        model.addAttribute("isMaitre", isMaitre);

        var allUsers = userService.getAllUsers();
        Map<String, String> waiterNames = new HashMap<>();
        allUsers.forEach(u -> waiterNames.put(String.valueOf(u.id()), u.username()));
        model.addAttribute("waiterNames", waiterNames);

        if (isMaitre) {
            var allWaiters = userService.getWaiters();
            model.addAttribute("activeWaiters", allWaiters.stream()
                .filter(w -> Boolean.TRUE.equals(w.active()))
                .toList());
        }

        return "private/bookings/manage";
    }

    @PostMapping("/toggle-active")
    public String toggleActive(@RequestParam Long id, @RequestParam String date, RedirectAttributes redirectAttributes) {
        try {
            bookingService.toggleActive(id);
            redirectAttributes.addFlashAttribute("success", "Stato prenotazione aggiornato");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/manage?date=" + date;
    }

    @PostMapping("/update-notes")
    public String updateNotes(
            @RequestParam Long id,
            @RequestParam String notes,
            @RequestParam String date,
            RedirectAttributes redirectAttributes) {
        try {
            BookingDTO dto = new BookingDTO(null, null, null, null, null, null, null, notes, null);
            bookingService.updateBooking(id, dto);
            redirectAttributes.addFlashAttribute("success", "Note aggiornate con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/manage?date=" + date;
    }

    @PostMapping("/delete")
    public String deleteBooking(
            @RequestParam Long id,
            @RequestParam String date,
            RedirectAttributes redirectAttributes) {
        try {
            bookingService.deleteBooking(id);
            redirectAttributes.addFlashAttribute("success", "Prenotazione eliminata con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/manage?date=" + date;
    }

    @PostMapping("/assign-waiter")
    public String assignWaiter(
            @RequestParam Long bookingId,
            @RequestParam Long waiterId,
            @RequestParam String date,
            RedirectAttributes redirectAttributes) {
        try {
            bookingService.addWaiter(bookingId, waiterId);
            redirectAttributes.addFlashAttribute("success", "Cameriere assegnato con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/manage?date=" + date;
    }

    @PostMapping("/remove-waiter")
    public String removeWaiter(
            @RequestParam Long bookingId,
            @RequestParam Long waiterId,
            @RequestParam String date,
            RedirectAttributes redirectAttributes) {
        try {
            bookingService.removeWaiter(bookingId, waiterId);
            redirectAttributes.addFlashAttribute("success", "Cameriere rimosso con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/manage?date=" + date;
    }

    @GetMapping("/add")
    public String getAddBooking(Model model) {
        model.addAttribute("isEdit", false);
        return "private/bookings/add";
    }

    @GetMapping("/edit")
    public String getEditBooking(
            @RequestParam Long id,
            Model model) {
        var booking = bookingService.getBookingById(id);
        if (booking.isEmpty()) {
            return "redirect:/bookings/manage";
        }
        model.addAttribute("booking", booking.get());
        model.addAttribute("isEdit", true);
        return "private/bookings/add";
    }

    @PostMapping("/add")
    public String postAddBooking(
            @RequestParam String customerName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String bookingDate,
            @RequestParam String bookingTime,
            @RequestParam Integer numberOfGuests,
            @RequestParam(required = false) String notes,
            RedirectAttributes redirectAttributes) {
        try {
            var date = java.time.LocalDate.parse(bookingDate);
            var time = java.time.LocalTime.parse(bookingTime);
            var dateTime = java.time.LocalDateTime.of(date, time);
            var bookingDTO = new com.javlom3.javabistrot.dto.BookingDTO(
                null,
                customerName.trim(),
                email.trim(),
                phoneNumber.trim(),
                numberOfGuests,
                dateTime,
                new java.util.HashSet<>(),
                notes != null ? notes.trim() : null,
                true
            );
            bookingService.createBooking(bookingDTO);
            redirectAttributes.addFlashAttribute("success", "Prenotazione aggiunta con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/manage";
    }

    @PostMapping("/update")
    public String postUpdateBooking(
            @RequestParam Long id,
            @RequestParam String customerName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String bookingDate,
            @RequestParam String bookingTime,
            @RequestParam Integer numberOfGuests,
            @RequestParam(required = false) String notes,
            RedirectAttributes redirectAttributes) {
        try {
            var date = java.time.LocalDate.parse(bookingDate);
            var time = java.time.LocalTime.parse(bookingTime);
            var dateTime = java.time.LocalDateTime.of(date, time);
            var bookingDTO = new com.javlom3.javabistrot.dto.BookingDTO(
                null,
                customerName.trim(),
                email.trim(),
                phoneNumber.trim(),
                numberOfGuests,
                dateTime,
                new java.util.HashSet<>(),
                notes != null ? notes.trim() : null,
                null
            );
            bookingService.updateBooking(id, bookingDTO);
            redirectAttributes.addFlashAttribute("success", "Prenotazione aggiornata con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/manage";
    }
}
