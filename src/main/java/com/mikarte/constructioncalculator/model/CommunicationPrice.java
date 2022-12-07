package com.mikarte.constructioncalculator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "communication_price")
public class CommunicationPrice {
    @Id
    private Long id;
    private Float installationHeatFloorPipes;
    private Float installationSewerPipes;
    private Float installationSourceWaterPipes;
    private Float installationHeatingPipes;
    private Float cableLaying;
    private Float installationBackupEmbeds;
    private Float installationWaterEmbeds;
    private Float installationGround;
    private Float manifoldInstallation;
    
    public float getSum() {
        float sum = 0;
        sum += installationHeatFloorPipes;
        sum += installationSewerPipes;
        sum += installationSourceWaterPipes;
        sum += installationHeatingPipes;
        sum += cableLaying;
        sum += installationBackupEmbeds;
        sum += installationWaterEmbeds;
        sum += installationGround;
        sum += manifoldInstallation;
        return sum;
    }
}
