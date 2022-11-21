package com.mikarte.constructioncalculator.repository;

import com.mikarte.constructioncalculator.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
}
