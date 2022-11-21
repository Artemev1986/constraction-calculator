package com.mikarte.constructioncalculator.service;

import com.mikarte.constructioncalculator.model.InputStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Calculator {

    private final Map<Long, Map<InputStatus, Float>> data = new HashMap<>();


}
