package com.javlom3.javabistrot.controllers;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javlom3.javabistrot.dto.DishDTO;
import com.javlom3.javabistrot.entities.DishType;
import com.javlom3.javabistrot.services.DishService;

@Controller
@RequestMapping("/menu")
public class MenuManageController {

    private final DishService dishService;

    public MenuManageController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/manage")
    public String manageMenu(Model model) {
        model.addAttribute("antipasti", dishService.getActiveDishesByType(DishType.ANTIPASTO));
        model.addAttribute("primi", dishService.getActiveDishesByType(DishType.PRIMO));
        model.addAttribute("secondi", dishService.getActiveDishesByType(DishType.SECONDO));
        model.addAttribute("dolci", dishService.getActiveDishesByType(DishType.DOLCE));
        model.addAttribute("hiddenDishes", dishService.getInactiveDishes());
        model.addAttribute("dishTypes", DishType.values());
        return "private/menu/manage";
    }

    @PostMapping("/add")
    public String addDish(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam BigDecimal price,
            @RequestParam DishType type,
            RedirectAttributes redirectAttributes) {
        try {
            DishDTO dto = new DishDTO(null, name, description, price, type, true);
            dishService.createDish(dto);
            redirectAttributes.addFlashAttribute("success", "Piatto aggiunto con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/menu/manage";
    }

    @PostMapping("/update")
    public String updateDish(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam BigDecimal price,
            @RequestParam DishType type,
            RedirectAttributes redirectAttributes) {
        try {
            DishDTO dto = new DishDTO(null, name, description, price, type, null);
            dishService.updateDish(id, dto);
            redirectAttributes.addFlashAttribute("success", "Piatto aggiornato con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/menu/manage";
    }

    @PostMapping("/toggle")
    public String toggleDish(
            @RequestParam Long id,
            @RequestParam Boolean active,
            RedirectAttributes redirectAttributes) {
        try {
            if (active) {
                dishService.activateDish(id);
            } else {
                dishService.deactivateDish(id);
            }
            String status = active ? "attivato" : "disattivato";
            redirectAttributes.addFlashAttribute("success", "Piatto " + status + " con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/menu/manage";
    }

    @PostMapping("/delete")
    public String deleteDish(
            @RequestParam Long id,
            RedirectAttributes redirectAttributes) {
        try {
            dishService.deleteDish(id);
            redirectAttributes.addFlashAttribute("success", "Piatto eliminato con successo");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/menu/manage";
    }
}
