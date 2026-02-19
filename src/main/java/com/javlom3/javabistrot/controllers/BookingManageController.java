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
        
        List<BookingDTO> bookings = bookingService.getByDay(selectedDate);
        int totalGuests = bookingService.countGuestsByDay(selectedDate);
        
        // Controlla se l'utente è MAITRE
        boolean isMaitre = authentication.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_MAITRE"));
        
        model.addAttribute("bookings", bookings);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("totalGuests", totalGuests);
        model.addAttribute("isMaitre", isMaitre);
        
        // Mappa ID -> Username per tutti gli utenti (camerieri e maitre)
        // Usiamo String come chiave perché Thymeleaf converte gli ID a String
        var allUsers = userService.getAllUsers();
        Map<String, String> waiterNames = new HashMap<>();
        allUsers.forEach(u -> waiterNames.put(String.valueOf(u.id()), u.username()));
        model.addAttribute("waiterNames", waiterNames);
        
        if (isMaitre) {
            // Passa solo i camerieri attivi per l'assegnazione
            var allWaiters = userService.getWaiters();
            model.addAttribute("activeWaiters", allWaiters.stream()
                .filter(w -> Boolean.TRUE.equals(w.active()))
                .toList());
        }
        
        return "private/bookings/manage";
    }

    @PostMapping("/update-notes")
    public String updateNotes(
            @RequestParam Long id,
            @RequestParam String notes,
            @RequestParam String date,
            RedirectAttributes redirectAttributes) {
        try {
            BookingDTO dto = new BookingDTO(null, null, null, null, null, null, null, notes);
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
}
