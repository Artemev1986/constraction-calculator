package com.mikarte.constructioncalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyAdditionalWorkAndEquipment {
    INSTALLATION_INSULATED_BLIND_AREA("Устройство утепленной отмостки"),
    INSTALLATION_DRAINAGE_DITCH("Устройство дренажной канавы с укладкой геотекстиля, отсыпкой щебня и укладкой дренажной трубы"),
    INSTALLATION_DRAINAGE_WELLS("Монтаж дренажных колодцев"),
    INSTALLATION_RAIN_INLETS("Монтаж дождеприемников"),
    INSTALLATION_STORM_SEWER_PIPES("Монтаж труб ливневой канализации"),
    INSTALLATION_SEPTIC("Монтаж септика"),
    INSTALLATION_CAISSON("Монтаж кессона"),
    BUSINESS_TRIP("Коммандировочные"),
    TRANSPORT("Транспортные"),
    BACKHOE_LOADER("Эксковатор-погрузчик"),
    CONCRETE_PUMP("Бетононасос");

    private final String title;
}
