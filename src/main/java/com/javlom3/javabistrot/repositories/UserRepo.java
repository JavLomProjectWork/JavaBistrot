package com.javlom3.javabistrot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javlom3.javabistrot.entities.User;

public interface UserRepo extends JpaRepository<User, Long>{
    
}
