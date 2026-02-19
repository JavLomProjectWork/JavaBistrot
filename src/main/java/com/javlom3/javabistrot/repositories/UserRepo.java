package com.javlom3.javabistrot.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javlom3.javabistrot.entities.Role;
import com.javlom3.javabistrot.entities.User;


public interface UserRepo extends JpaRepository<User, Long>{
    
    public Optional<User> findByUsername(String username);
    public List<User> findByRole(Role role);
}
