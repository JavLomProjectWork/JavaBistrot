package com.javlom3.javabistrot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javlom3.javabistrot.entities.Dish;
import com.javlom3.javabistrot.entities.DishType;

import java.util.List;

public interface DishRepo extends JpaRepository<Dish, Long> {
    List<Dish> findByType(DishType type);

    List<Dish> findByNameContainingIgnoreCase(String name);
}
