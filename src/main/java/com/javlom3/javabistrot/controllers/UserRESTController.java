package com.javlom3.javabistrot.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javlom3.javabistrot.dto.UserDTO;
import com.javlom3.javabistrot.entities.Role;
import com.javlom3.javabistrot.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/users")
@Slf4j
public class UserRESTController {

    private final UserService userService;

    public UserRESTController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/adduser")
    public String createUserFromPostman(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Role role,
            @RequestParam(defaultValue = "true") Boolean active) {
        UserDTO dto = new UserDTO(null, username, role, active);
        var newUser = userService.createUser(dto, password);
        log.info("Nuovo utente creato: {}", newUser);
        return "redirect:/test";
    }

    @PostMapping("/removeuser")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        log.info("Utente con id {} eliminato", id);
        return "redirect:/test";
    }

    @PostMapping("/updateuser")
    public String updateUser(
            @RequestParam Long id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Boolean active) {
        UserDTO dto = new UserDTO(null, username, role, active);
        var updatedUser = userService.updateUser(id, dto, password);
        if (updatedUser.isPresent()) {
            log.info("Utente con id {} aggiornato: {}", id, updatedUser.get());
        } else {
            throw new IllegalArgumentException("Utente con id " + id + " non trovato");
        }
        return "redirect:/test";
    }
}
