package com.mikarte.constructioncalculator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "main_working_price")
public class MainWorkingPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float breakdownOfAxes;
    private Float developmentOfPit;
    private Float equippingPillow;
    private Float layingWaterproof;
    private Float layingGeotextile;
    private Float insulationFirstLayer;
    private Float insulationSecondLayer;
    private Float installationPlate;
}
