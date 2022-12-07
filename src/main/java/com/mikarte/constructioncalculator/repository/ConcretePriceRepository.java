package com.mikarte.constructioncalculator.repository;

import com.mikarte.constructioncalculator.model.ConcretePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcretePriceRepository extends JpaRepository<ConcretePrice, Long> {
}
