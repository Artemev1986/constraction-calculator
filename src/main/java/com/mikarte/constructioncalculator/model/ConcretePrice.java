package com.mikarte.constructioncalculator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "concrete_price")
public class ConcretePrice {
    @Id
    private Long id;
    private Float formworkAssemblyDisassembly;
    private Float installationFormworkSteps;
    private Float ribReinforcement;
    private Float slabReinforcement;
    private Float concreting;
    private Float polesForTerrace;
    private Float concreteGrinding;

    public float getSum() {
        float sum = 0;
        sum += formworkAssemblyDisassembly;
        sum += installationFormworkSteps;
        sum += ribReinforcement;
        sum += slabReinforcement;
        sum += concreting;
        sum += polesForTerrace;
        sum += concreteGrinding;
        return sum;
    }
}
