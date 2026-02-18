package com.javlom3.javabistrot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        
        if (error != null) {
            model.addAttribute("errorMessage", "Username o password non validi");
        }
        
        if (logout != null) {
            model.addAttribute("logoutMessage", "Logout effettuato con successo");
        }
        
        return "auth/login";
    }
}
