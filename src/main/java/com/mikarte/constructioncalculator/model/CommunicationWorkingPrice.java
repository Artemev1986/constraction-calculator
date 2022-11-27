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
public class CommunicationWorkingPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
