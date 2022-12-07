package com.mikarte.constructioncalculator.repository;

import com.mikarte.constructioncalculator.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
    default Questionnaire getById(Long id) {
        return findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Questionnaire with id (" + id + ") not found"))
        );
    }

    Questionnaire findFirstByChatIdOrderByIdDesc(long chatId);
}
