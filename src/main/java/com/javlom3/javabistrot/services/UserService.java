package com.javlom3.javabistrot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javlom3.javabistrot.dto.UserDTO;
import com.javlom3.javabistrot.entities.User;
import com.javlom3.javabistrot.mapper.UserMapper;
import com.javlom3.javabistrot.repositories.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .filter(user -> Boolean.TRUE.equals(user.getActive()))
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato o non attivo: " + username));
    }

    @Transactional
    public Optional<UserDTO> createUser(UserDTO userDTO, String password) {
        // Verifica se l'username esiste già
        if (userRepo.findByUsername(userDTO.username()).isPresent()) {
            throw new IllegalArgumentException("Username " + userDTO.username() + " già in uso");
        }
        
        User newUser = userMapper.toEntity(userDTO);
        newUser.setActive(userDTO.active());
        newUser.setPassword(passwordEncoder.encode(password));

        UserDTO savedUser = userMapper.toDto(userRepo.save(newUser));
        return Optional.of(savedUser);
    }

    

    @Transactional
    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO, String password) {
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
            if (password != null) {
                existing.setPassword(passwordEncoder.encode(password));
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

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new IllegalArgumentException("Utente con id " + id + " non trovato");
        }
        userRepo.deleteById(id);
    }

    @Transactional
    public List<UserDTO> getWaiters() {
        return userRepo.findByRole(com.javlom3.javabistrot.entities.Role.WAITER)
            .stream()
            .map(userMapper::toDto)
            .toList();
    }

    @Transactional
    public List<UserDTO> getMaitres() {
        return userRepo.findByRole(com.javlom3.javabistrot.entities.Role.MAITRE)
            .stream()
            .map(userMapper::toDto)
            .toList();
    }
}
