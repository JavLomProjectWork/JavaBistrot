package com.javlom3.javabistrot.mapper;

import org.springframework.stereotype.Component;

import com.javlom3.javabistrot.dto.DishDTO;
import com.javlom3.javabistrot.entities.Dish;

@Component
public class DishMapper {

    public Dish toEntity(DishDTO dto) {
        Dish entity = new Dish();
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());
        entity.setType(dto.type());
        entity.setActive(dto.active() != null ? dto.active() : true);
        return entity;
    }

    public DishDTO toDto(Dish entity) {
        return new DishDTO(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getPrice(),
            entity.getType(),
            entity.getActive()
        );
    }
}
