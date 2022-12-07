package com.mikarte.constructioncalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InputStatus {
    NONE(""),
    SLAB_PERIMETER("Периметр плиты,м"),
    SLAB_AREA_FULL("Площадь плиты с крыльцом и террасой,м2"),
    SLAB_AREA_BASE("Площадь плиты без крыльца и террасы,м2"),
    SLAB_AREA_HEATED("Отапливаемая площадь плиты,м2"),
    NUMBER_OF_AXES("Количество осей,шт"),
    ROUTE_LENGTH("Длина подводящих трасс: воды, электричества, канализации,м"),
    SEWER_RISERS("Канализационные стояки 110,шт"),
    DRAW_OFF_POINTS("Точки водоразбора,шт"),
    CABLE_ENTRY("Кабельный ввод,шт"),
    GROUND("Защитное заземление,шт"),
    //LENGTH_OUTSIDE_WALL("Длина наружных несущих стен"),
    LENGTH_INSIDE_WALL("Длина внутренних несущих стен,м"),
    PLATE_RIB_WIDTH("Ширина ребер плиты,м"),
    //RIB_VOLUME("Объем ребер"),
    //PIT_AREA("Площадь котлована"),
    PIT_DEPTH("Глубина котлована,м"),
    //BLIND_AREA("Отмостка"),
    INSULATION_THICKNESS("Толщина утеплителя,м"),
    SAND_BED_THICKNESS("Толщина песчаной подсыпки,м");

    private final String title;
}
