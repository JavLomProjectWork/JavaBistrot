package com.javlom3.javabistrot.dto;

import com.javlom3.javabistrot.entities.Role;

public record UserDTO(
    Long id,
    String username,
    Role role
) {}
