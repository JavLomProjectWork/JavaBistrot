package com.javlom3.javabistrot.controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javlom3.javabistrot.dto.BookingDTO;
import com.javlom3.javabistrot.services.BookingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("api/bookings")
public class BookingRESTController {
    
    private final BookingService bookingService;

    public BookingRESTController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping("/addbooking")
    public String createBooking(
        @RequestParam String customerName,
        @RequestParam String email,
        @RequestParam String phoneNumber,
        @RequestParam Integer numberOfGuests,
        @RequestParam LocalDateTime bookingDateTime,
        @RequestParam(required = false) String notes
    )
    {
        BookingDTO dto = new BookingDTO(null,customerName,email,phoneNumber,numberOfGuests,bookingDateTime,null,notes);
        Optional<BookingDTO> newBooking = bookingService.createBooking(dto);
        log.info("Nuova prenotazione creata {}", newBooking);
        return "redirect:/test";
    }
    
    @PostMapping("/removebooking")
    public String deleteBooking(@RequestParam Long id) {
        bookingService.deleteBooking(id);
        log.info("Prenotazione con id {} eliminata", id);
        return "redirect:/test";
    }
    
    @PostMapping("/updatebooking")
    public String updateBooking(
        @RequestParam Long id,
        @RequestParam(required = false) String customerName,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String phoneNumber,
        @RequestParam(required = false)Integer numberOfGuests,
        @RequestParam(required = false)LocalDateTime bookingDateTime,
        @RequestParam(required = false) String notes
    )
    {
        BookingDTO dto = new BookingDTO(null,customerName,email,phoneNumber,numberOfGuests,bookingDateTime,null,notes);
        Optional<BookingDTO> newBooking = bookingService.updateBooking(id, dto);
        if(newBooking.isPresent()){
            log.info("Nuova prenotazione creata {}", newBooking);
        } else{
            throw new IllegalArgumentException("Prenotazione con id " + id  + " non trovata");
        }
       
        return "redirect:/test";
    }
    
    @PostMapping("/addwaiter")
    public String addWaiterToBooking(
        @RequestParam Long idWaiter,
        @RequestParam Long idBooking
    ) {
        Optional<BookingDTO> dto = bookingService.addWaiter(idBooking, idWaiter);
        if(dto.isPresent()){
            log.info("Aggiunto cameriere {}", dto);
        } else{
            throw new IllegalArgumentException("Impossibile aggiungere il cameriere con id " + idWaiter  + " alla prenotazione con id " + idBooking);
        }
        return "redirect:/test";
    }
    
    @PostMapping("/removewaiter")
    public String removeWaiterToBooking(
        @RequestParam Long idWaiter,
        @RequestParam Long idBooking
    ) {
        Optional<BookingDTO> dto = bookingService.removeWaiter(idBooking, idWaiter);
        if(dto.isPresent()){
            log.info("Rimosso cameriere {}", dto);
        } else{
            throw new IllegalArgumentException("Impossibile rimuovere il cameriere con id " + idWaiter  + " alla prenotazione con id " + idBooking);
        }
        return "redirect:/test";
    }
    

}
