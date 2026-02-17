package com.javlom3.javabistrot.mapper;

import org.springframework.stereotype.Component;

import com.javlom3.javabistrot.dto.UserDTO;
import com.javlom3.javabistrot.entities.User;

@Component
public class UserMapper {
    
    public User toEntity(UserDTO dto) {
        User entity = new User();
        entity.setUsername(dto.username());
        entity.setRole(dto.role());
        return entity;
    }
    
    public UserDTO toDto(User entity) {
        return new UserDTO(
            entity.getId(),
            entity.getUsername(),
            entity.getRole(),
            entity.getActive()
        );
    }

}
