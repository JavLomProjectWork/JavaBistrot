package com.javlom3.javabistrot.mapper;

import com.javlom3.javabistrot.dto.UserDTO;
import com.javlom3.javabistrot.entities.User;

public class UserMapper {
    
    public User toEntity(UserDTO dto) {
        User entity = new User();
        entity.setUsername(dto.username());
        entity.setPassword(dto.password());
        entity.setRole(dto.role());
        return entity;
    }
    
    public UserDTO toDto(User entity) {
        return new UserDTO(
           entity.getId(),
           entity.getUsername(),
           entity.getPassword(),
           entity.getRole(),
           entity.getBookings() 
        );
    }

}
