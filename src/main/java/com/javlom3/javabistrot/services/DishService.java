package com.javlom3.javabistrot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.javlom3.javabistrot.dto.DishDTO;
import com.javlom3.javabistrot.entities.Dish;
import com.javlom3.javabistrot.entities.DishType;
import com.javlom3.javabistrot.mapper.DishMapper;
import com.javlom3.javabistrot.repositories.DishRepo;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DishService {

    private final DishRepo dishRepo;
    private final DishMapper dishMapper;

    public DishService(DishRepo dishRepo, DishMapper dishMapper) {
        this.dishRepo = dishRepo;
        this.dishMapper = dishMapper;
    }

    @Transactional
    public DishDTO createDish(DishDTO dto) {
        log.info("createDish name={}", dto.name());
        Dish entity = dishMapper.toEntity(dto);
        Dish saved = dishRepo.save(entity);
        return dishMapper.toDto(saved);
    }

    @Transactional
    public Optional<DishDTO> getDishById(Long id) {
        return dishRepo.findById(id).map(dishMapper::toDto);
    }

    @Transactional
    public List<DishDTO> getAllDishes() {
        return dishRepo.findAll().stream().map(dishMapper::toDto).toList();
    }

    @Transactional
    public List<DishDTO> getDishesByType(DishType type) {
        return dishRepo.findByType(type).stream().map(dishMapper::toDto).toList();
    }

    @Transactional
    public List<DishDTO> searchDishesByName(String name) {
        return dishRepo.findByNameContainingIgnoreCase(name).stream().map(dishMapper::toDto).toList();
    }

    @Transactional
    public Optional<DishDTO> updateDish(Long id, DishDTO dto) {
        log.info("updateDish id={}", id);
        return dishRepo.findById(id).map(existing -> {
            if (dto.name() != null) {
                existing.setName(dto.name());
            }
            if (dto.description() != null) {
                existing.setDescription(dto.description());
            }
            if (dto.price() != null) {
                existing.setPrice(dto.price());
            }
            if (dto.type() != null) {
                existing.setType(dto.type());
            }
            if (dto.active() != null) {
                existing.setActive(dto.active());
            }
            Dish updated = dishRepo.save(existing);
            return dishMapper.toDto(updated);
        });
    }

    @Transactional
    public boolean deleteDish(Long id) {
        log.info("deleteDish id={}", id);
        if (dishRepo.existsById(id)) {
            dishRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public List<DishDTO> getActiveDishesByType(DishType type) {
        return dishRepo.findByTypeAndActiveTrue(type).stream().map(dishMapper::toDto).toList();
    }

    @Transactional
    public List<DishDTO> getAllActiveDishes() {
        return dishRepo.findByActiveTrue().stream().map(dishMapper::toDto).toList();
    }

    @Transactional
    public Optional<DishDTO> toggleActive(Long id) {
        log.info("toggleActive id={}", id);
        return dishRepo.findById(id).map(dish -> {
            dish.setActive(!dish.getActive());
            Dish updated = dishRepo.save(dish);
            return dishMapper.toDto(updated);
        });
    }

    @Transactional
    public Optional<DishDTO> activateDish(Long id) {
        log.info("activateDish id={}", id);
        return dishRepo.findById(id).map(dish -> {
            dish.setActive(true);
            Dish updated = dishRepo.save(dish);
            return dishMapper.toDto(updated);
        });
    }

    @Transactional
    public Optional<DishDTO> deactivateDish(Long id) {
        log.info("deactivateDish id={}", id);
        return dishRepo.findById(id).map(dish -> {
            dish.setActive(false);
            Dish updated = dishRepo.save(dish);
            return dishMapper.toDto(updated);
        });
    }

    @Transactional
    public List<DishDTO> getInactiveDishes() {
        log.info("getInactiveDishes");
        return dishRepo.findByActiveFalse().stream().map(dishMapper::toDto).toList();
    }
}
