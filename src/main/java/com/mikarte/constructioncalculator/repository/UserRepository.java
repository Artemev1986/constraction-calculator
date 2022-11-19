package com.mikarte.constructioncalculator.repository;

import com.mikarte.constructioncalculator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

public interface UserRepository extends JpaRepository<User, Long> {

    default User getById(Long id) {
        return findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id (" + id + ") not found"))
        );
    }
}
