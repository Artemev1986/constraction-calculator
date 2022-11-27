package com.mikarte.constructioncalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InputStatus {
    NONE(""),
    SLAB_PERIMETER("Периметр плиты"),
    SLAB_AREA_FULL("Площадь плиты с крыльцом и террасой"),
    SLAB_AREA_BASE("Площадь плиты без крыльца и террасы"),
    SLAB_AREA_HEATED("Отапливаемая площадь плиты"),
    NUMBER_OF_AXES("Количество осей"),
    ROUTE_LENGTH("Длина подводящих трасс: воды, электричества, канализации"),
    SEWER_RISERS("Канализационные стояки 110"),
    DRAW_OFF_POINTS("Точки водоразбора"),
    CABLE_ENTRY("Кабельный ввод"),
    GROUND("Защитное заземление"),
    //LENGTH_OUTSIDE_WALL("Длина наружных несущих стен"),
    LENGTH_INSIDE_WALL("Длина внутренних несущих стен"),
    PLATE_RIB_WIDTH("Ширина ребер плиты"),
    //RIB_VOLUME("Объем ребер"),
    //PIT_AREA("Площадь котлована"),
    PIT_DEPTH("Глубина котлована"),
    //BLIND_AREA("Отмостка"),
    INSULATION_THICKNESS("Толщина утеплителя"),
    SAND_BED_THICKNESS("Толщина песчаной подсыпки");

    private final String title;
}
