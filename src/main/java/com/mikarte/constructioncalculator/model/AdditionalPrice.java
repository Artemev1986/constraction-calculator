package com.mikarte.constructioncalculator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "additional_price")
public class AdditionalPrice {
    @Id
    private Long id;
    private Float installationInsulatedBlindArea;
    private Float installationDrainageDitch;
    private Float installationDrainageWells;
    private Float installationRainInlets;
    private Float installationStormSewerPipes;
    private Float installationSeptic;
    private Float installationCaisson;
    private Float businessTrip;
    private Float transport;
    private Float backhoeLoader;
    private Float concretePump;

    public float getSum() {
        float sum = 0;
        sum += installationInsulatedBlindArea;
        sum += installationDrainageDitch;
        sum += installationDrainageWells;
        sum += installationRainInlets;
        sum += installationStormSewerPipes;
        sum += installationSeptic;
        sum += installationCaisson;
        sum += businessTrip;
        sum += transport;
        sum += backhoeLoader;
        sum += concretePump;
        return sum;
    }
}
