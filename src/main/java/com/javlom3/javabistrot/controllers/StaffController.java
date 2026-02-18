package com.javlom3.javabistrot.controllers;

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

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final UserService userService;

    public StaffController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/manage")
    public String manageStaff(Model model) {
        model.addAttribute("waiters", userService.getWaiters());
        model.addAttribute("maitres", userService.getMaitres());
        return "staff/manage";
    }

    @PostMapping("/add")
    public String addWaiter(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        try {
            UserDTO dto = new UserDTO(null, username, Role.WAITER, true);
            userService.createUser(dto, password);
            redirectAttributes.addFlashAttribute("success", "Cameriere aggiunto con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/staff/manage";
    }

    @PostMapping("/add-maitre")
    public String addMaitre(
            @RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        try {
            UserDTO dto = new UserDTO(null, username, Role.MAITRE, true);
            userService.createUser(dto, password);
            redirectAttributes.addFlashAttribute("success", "Maître aggiunto con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/staff/manage";
    }

    @PostMapping("/toggle")
    public String toggleWaiter(
            @RequestParam Long id,
            @RequestParam Boolean active,
            RedirectAttributes redirectAttributes) {
        try {
            UserDTO dto = new UserDTO(null, null, null, active);
            userService.updateUser(id, dto, null);
            String status = active ? "attivato" : "disattivato";
            redirectAttributes.addFlashAttribute("success", "Cameriere " + status + " con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/staff/manage";
    }

    @PostMapping("/delete")
    public String deleteWaiter(
            @RequestParam Long id,
            RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "Cameriere eliminato con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/staff/manage";
    }
}
