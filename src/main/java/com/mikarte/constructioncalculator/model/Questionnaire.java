package com.mikarte.constructioncalculator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Questionnaires")
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;
    private Float slabPerimeter;
    private Float slabAreaFull;
    private Float slabAreaBase;
    private Float slabAreaHeated;
    private Float numberOfAxes;
    private Float routeLength;
    private Float sewerRisers;
    private Float drawOffPoints;
    private Float cableEntry;
    private Float ground;
    @Transient
    private Float lengthOutsideWall;
    private Float lengthInsideWall;
    private Float plateRibWidth;
    @Transient
    private Float ribVolume;
    @Transient
    private Float pitArea;
    private Float pitDepth;
    @Transient
    private Float blindArea;
    private Float insulationThickness;
    private Float sandBedThickness;
    private LocalDateTime createdOn;
}
