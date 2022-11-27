package com.mikarte.constructioncalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyCommunicationWorking {
    INSTALLATION_HEAT_FLOOR_PIPES("Монтаж трубы водяного теплого пола"),
    INSTALLATION_SEWER_PIPES("Монтаж канализационных труб"),
    INSTALLATION_SOURCE_WATER_PIPES("Монтаж труб водоснабжения"),
    INSTALLATION_HEATING_PIPES("Монтаж труб отопления"),
    CABLE_LAYING("Прокладка кабеля до (электрощитка) в защитной трубе"),
    INSTALLATION_BACKUP_EMBEDS("Устройство резервных закладных труб для ввода/вывода коммуникаций"),
    INSTALLATION_WATER_EMBEDS("Ввод/вывод трубы водоснабжения в защитной трубе"),
    INSTALLATION_GROUND("Монтаж заземления"),
    MANIFOLD_INSTALLATION("Монтаж коллектора и опрессовка системы ТП");

    private final String title;
}
