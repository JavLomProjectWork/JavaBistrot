package com.javlom3.javabistrot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.javlom3.javabistrot.dto.UserDTO;
import com.javlom3.javabistrot.entities.User;
import com.javlom3.javabistrot.mapper.UserMapper;
import com.javlom3.javabistrot.repositories.UserRepo;

import jakarta.transaction.Transactional;

public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<UserDTO> createUser(UserDTO userDTO, String password) {
        User newUser = userMapper.toEntity(userDTO);
        newUser.setPassword(passwordEncoder.encode(password));

        UserDTO savedUser = userMapper.toDto(userRepo.save(newUser));
        return Optional.of(savedUser);
    }

    

    @Transactional
    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        Optional<User> userToUpdate = userRepo.findById(id);
        if(userToUpdate.isPresent()){
            User newUser = userMapper.toEntity(userDTO);
            newUser.setId(id);
            User updatedUser = userRepo.save(newUser);
            return Optional.of(userMapper.toDto(updatedUser));
        }
        return Optional.empty();
    }

    @Transactional
    public List<UserDTO> getAllUsers(){
        return userRepo.findAll().stream().map(userMapper::toDto).toList();
    }

    @Transactional
    public Optional<UserDTO> getUserByUsername(String username){
        return userRepo.findByUsername(username).map(userMapper::toDto);
    }





}
