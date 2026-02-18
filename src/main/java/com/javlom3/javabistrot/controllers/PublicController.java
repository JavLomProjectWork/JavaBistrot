package com.javlom3.javabistrot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {
    
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }
    
    @GetMapping("/menu")
    public String getMenu() {
        return "menu";
    }


    @GetMapping("/home")
    public String getHome() {
        return "home";
    }
}
