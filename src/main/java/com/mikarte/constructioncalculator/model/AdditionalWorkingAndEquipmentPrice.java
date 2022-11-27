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
public class AdditionalWorkingAndEquipmentPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
