package com.javlom3.javabistrot.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javlom3.javabistrot.dto.UserDTO;
import com.javlom3.javabistrot.entities.Role;
import com.javlom3.javabistrot.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/staff")
@PreAuthorize("hasRole('MAITRE')")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String staffPage(Model model) {
        model.addAttribute("waiters", userService.getWaiters());
        model.addAttribute("maitres", userService.getMaitres());
        model.addAttribute("roles", Role.values());
        return "staff/manage";
    }

    @PostMapping("/add")
    public String createUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Role role,
            @RequestParam(defaultValue = "true") Boolean active,
            RedirectAttributes redirectAttributes) {
        try {
            UserDTO dto = new UserDTO(null, username, role, active);
            userService.createUser(dto, password);
            String roleLabel = role == Role.MAITRE ? "Maitre" : "Cameriere";
            log.info("Nuovo {} creato: {}", roleLabel.toLowerCase(), username);
            redirectAttributes.addFlashAttribute("successMessage", roleLabel + " " + username + " creato con successo");
        } catch (IllegalArgumentException e) {
            log.error("Errore creazione utente: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/staff";
    }

    @PostMapping("/remove")
    public String deleteUser(
            @RequestParam Long id,
            RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            log.info("Utente con id {} eliminato", id);
            redirectAttributes.addFlashAttribute("successMessage", "Utente eliminato con successo");
        } catch (IllegalArgumentException e) {
            log.error("Errore eliminazione utente: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/staff";
    }

    @PostMapping("/update")
    public String updateUser(
            @RequestParam Long id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Boolean active,
            RedirectAttributes redirectAttributes) {
        try {
            UserDTO dto = new UserDTO(null, username, role, active);
            var updatedUser = userService.updateUser(id, dto, password);
            if (updatedUser.isPresent()) {
                log.info("Utente con id {} aggiornato: {}", id, updatedUser.get());
                redirectAttributes.addFlashAttribute("successMessage", "Utente aggiornato con successo");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Utente con id " + id + " non trovato");
            }
        } catch (Exception e) {
            log.error("Errore aggiornamento utente: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/staff";
    }

    @PostMapping("/toggle")
    public String toggleStatus(
            @RequestParam Long id,
            @RequestParam Boolean active,
            RedirectAttributes redirectAttributes) {
        try {
            UserDTO dto = new UserDTO(null, null, null, active);
            userService.updateUser(id, dto, null);
            log.info("Utente con id {} stato aggiornato a: {}", id, active);
            redirectAttributes.addFlashAttribute("successMessage", "Stato utente aggiornato");
        } catch (Exception e) {
            log.error("Errore aggiornamento stato: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/staff";
    }
}
