package com.javlom3.javabistrot.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.javlom3.javabistrot.entities.User;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "dashboard";
    }
}
