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
@Table(name = "full_price")
public class FullPrice {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "main_price_id")
    private MainPrice mainPrice;
    @ManyToOne
    @JoinColumn(name = "communication_price_id")
    private CommunicationPrice communicationPrice;
    @ManyToOne
    @JoinColumn(name = "concrete_price_id")
    private ConcretePrice concretePrice;
    @ManyToOne
    @JoinColumn(name = "additional_price_id")
    private AdditionalPrice additionalPrice;
    private LocalDateTime updated;
}
