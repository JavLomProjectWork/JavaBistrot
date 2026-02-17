package com.javlom3.javabistrot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javlom3.javabistrot.dto.UserDTO;
import com.javlom3.javabistrot.entities.User;
import com.javlom3.javabistrot.mapper.UserMapper;
import com.javlom3.javabistrot.repositories.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<UserDTO> createUser(UserDTO userDTO, String password) {
        User newUser = userMapper.toEntity(userDTO);
        newUser.setActive(userDTO.active());
        newUser.setPassword(passwordEncoder.encode(password));

        UserDTO savedUser = userMapper.toDto(userRepo.save(newUser));
        return Optional.of(savedUser);
    }

    

    @Transactional
    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        return userRepo.findById(id).map(existing -> {
            if (userDTO.username() != null) {
                existing.setUsername(userDTO.username());
            }
            if (userDTO.role() != null) {
                existing.setRole(userDTO.role());
            }
            if (userDTO.active() != null) {
                existing.setActive(userDTO.active());
            }
            User updatedUser = userRepo.save(existing);
            return userMapper.toDto(updatedUser);
        });
    }

    @Transactional
    public List<UserDTO> getAllUsers(){
        return userRepo.findAll().stream().map(userMapper::toDto).toList();
    }

    @Transactional
    public Optional<UserDTO> getUserByUsername(String username){
        return userRepo.findByUsername(username).map(userMapper::toDto);
    }

    @Transactional
    public Optional<UserDTO> findByUsernameAndPassword(String username, String password) {
        return userRepo.findByUsername(username)
            .filter(user -> passwordEncoder.matches(password, user.getPassword()))
            .map(userMapper::toDto);
    }





}
