package com.mikarte.constructioncalculator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "main_price")
public class MainPrice {
    @Id
    private Long id;
    private Float breakdownOfAxes;
    private Float developmentOfPit;
    private Float equippingPillow;
    private Float layingWaterproof;
    private Float layingGeotextile;
    private Float insulationFirstLayer;
    private Float insulationSecondLayer;
    private Float installationPlate;
    
    public float getSum() {
        float sum = 0;
        sum += breakdownOfAxes;
        sum += developmentOfPit;
        sum += equippingPillow;
        sum += layingWaterproof;
        sum += layingGeotextile;
        sum += insulationFirstLayer;
        sum += insulationSecondLayer;
        sum += installationPlate;
        return sum;
    }
}
