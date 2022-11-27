package com.mikarte.constructioncalculator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table
public class ConcreteWorkingPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float formworkAssemblyDisassembly;
    private Float installationFormworkSteps;
    private Float ribReinforcement;
    private Float slabReinforcement;
    private Float concreting;
    private Float polesForTerrace;
    private Float concreteGrinding;
}
