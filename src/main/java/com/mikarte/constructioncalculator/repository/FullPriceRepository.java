package com.mikarte.constructioncalculator.repository;

import com.mikarte.constructioncalculator.model.FullPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

public interface FullPriceRepository extends JpaRepository<FullPrice, Long> {
    default FullPrice getById(Long id) {
        return findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Full price with id (" + id + ") not found"))
        );
    }
}
