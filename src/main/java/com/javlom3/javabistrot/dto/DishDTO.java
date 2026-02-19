package com.javlom3.javabistrot.dto;

import java.math.BigDecimal;

import com.javlom3.javabistrot.entities.DishType;

public record DishDTO(
    Long id,
    String name,
    String description,
    BigDecimal price,
    DishType type
) {}
