package com.mikarte.constructioncalculator.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Button {
    NEXT("Следующий параметр"),
    EXIT("Завершить ввод данных");
    private final String title;
}
