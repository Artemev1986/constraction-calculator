package com.mikarte.constructioncalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyMainWorking {
    BREAKDOWN_OF_AXES("Разбивка осей здания и вынос их в натуру"),
    DEVELOPMENT_OF_PIT("Разработка котлована под фундамент, планировка участка экскаватором, траншеи, разметка, технадзор"),
    EQUIPPING_PILLOW("Устройство  подушки с проливанием и уплотнением виброплитой, виброплита входит в стоимость"),
    LAYING_WATERPROOF("Укладка защитной п/э пленки для гидроизоляции фундамента"),
    LAYING_GEOTEXTILE("Укладка защитного геотекстиля"),
    INSULATION_FIRST_LAYER("Укладка утеплителя первый слой"),
    INSULATION_SECOND_LAYER("Укладка утеплителя второй слой"),
    INSTALLATION_PLATE("Сборка и монтаж боковых элементов УШП");
    private final String title;
}
