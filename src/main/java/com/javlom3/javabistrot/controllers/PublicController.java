package com.javlom3.javabistrot.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javlom3.javabistrot.dto.BookingDTO;
import com.javlom3.javabistrot.entities.DishType;
import com.javlom3.javabistrot.services.BookingService;
import com.javlom3.javabistrot.services.DishService;

@Controller
public class PublicController {
    
    private final BookingService bookingService;
    private final DishService dishService;
    
    public PublicController(BookingService bookingService, DishService dishService) {
        this.bookingService = bookingService;
        this.dishService = dishService;
    }
    
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }
    
    @GetMapping("/menu")
    public String getMenu(Model model) {
        model.addAttribute("activePage", "menu");
        model.addAttribute("antipasti", dishService.getActiveDishesByType(DishType.ANTIPASTO));
        model.addAttribute("primi", dishService.getActiveDishesByType(DishType.PRIMO));
        model.addAttribute("secondi", dishService.getActiveDishesByType(DishType.SECONDO));
        model.addAttribute("dolci", dishService.getActiveDishesByType(DishType.DOLCE));
        return "menu";
    }

    @GetMapping("/home")
    public String getHome(Model model) {
        model.addAttribute("activePage", "home");
        return "home";
    }
    
    @GetMapping("/prenota")
    public String getPrenota(Model model) {
        model.addAttribute("activePage", "prenota");
        return "prenota";
    }
    
    @PostMapping("/prenota")
    public String submitPrenota(
            @RequestParam String customerName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String bookingDate,
            @RequestParam String bookingTime,
            @RequestParam Integer numberOfGuests,
            @RequestParam(required = false) String notes,
            RedirectAttributes redirectAttributes) {
        
        try {
            LocalDate date = LocalDate.parse(bookingDate);
            LocalTime time = LocalTime.parse(bookingTime);
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            
            // Validazioni base
            if (customerName == null || customerName.trim().isEmpty()) {
                throw new IllegalArgumentException("Il nome è obbligatorio");
            }
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Email non valida");
            }
            if (numberOfGuests == null || numberOfGuests < 1) {
                throw new IllegalArgumentException("Numero ospiti non valido");
            }
            if (dateTime.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("La data deve essere futura");
            }
            
            BookingDTO bookingDTO = new BookingDTO(
                null,
                customerName.trim(),
                email.trim(),
                phoneNumber.trim(),
                numberOfGuests,
                dateTime,
                new HashSet<>(),
                notes != null ? notes.trim() : null
            );
            
            bookingService.createBooking(bookingDTO);
            
            redirectAttributes.addFlashAttribute("success", "Prenotazione effettuata con successo! Ti aspettiamo.");
            return "redirect:/prenota";
            
        } catch (Exception e) {
            // Mantieni i valori inseriti
            String errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.contains("phone")) {
                errorMessage = "Numero di telefono non valido";
            }
            redirectAttributes.addFlashAttribute("error", errorMessage);
            redirectAttributes.addFlashAttribute("customerName", customerName);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("phoneNumber", phoneNumber);
            redirectAttributes.addFlashAttribute("bookingDate", bookingDate);
            redirectAttributes.addFlashAttribute("bookingTime", bookingTime);
            redirectAttributes.addFlashAttribute("numberOfGuests", numberOfGuests);
            redirectAttributes.addFlashAttribute("notes", notes);
            return "redirect:/prenota";
        }
    }
}
