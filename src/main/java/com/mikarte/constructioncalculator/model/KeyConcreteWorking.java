package com.mikarte.constructioncalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyConcreteWorking {
    FORMWORK_ASSEMBLY_DISASSEMBLY("Сборка и разборка опалубки"),
    INSTALLATION_FORMWORK_STEPS("Монтаж опалубки ступеней"),
    RIB_REINFORCEMENT("Армирование ребер"),
    SLAB_REINFORCEMENT("Армирование плиты и экстраармирование"),
    CONCRETING("Бетонирование, установка маяков, приемка бетона, выравнивание гладилками, демонтаж маяков"),
    POLES_FOR_TERRACE("Столбики под террасу"),
    CONCRETE_GRINDING("Шлифовка бетона, бетонозатирочная машина входит в стоимость и устройство топпинга");

    private final String title;
}
