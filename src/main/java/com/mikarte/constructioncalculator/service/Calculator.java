package com.mikarte.constructioncalculator.service;

import com.mikarte.constructioncalculator.model.MainWorkingPrice;
import com.mikarte.constructioncalculator.model.Questionnaire;

public class Calculator {

    public static MainWorkingPrice calcMainWorking(MainWorkingPrice unitPrice, Questionnaire questionnaire) {
        MainWorkingPrice price = new MainWorkingPrice();
        price.setBreakdownOfAxes(unitPrice.getBreakdownOfAxes() * questionnaire.getNumberOfAxes());
        price.setDevelopmentOfPit(unitPrice.getDevelopmentOfPit());
        price.setEquippingPillow(unitPrice.getLayingWaterproof() * questionnaire.getPitArea() *
                questionnaire.getSandBedThickness());
        price.setLayingWaterproof(unitPrice.getLayingGeotextile() * questionnaire.getPitArea() * 1.3f);
        price.setLayingGeotextile(unitPrice.getLayingGeotextile() * questionnaire.getPitArea() * 1.3f);
        price.setInsulationFirstLayer(unitPrice.getInsulationFirstLayer() * questionnaire.getSlabAreaFull());
        price.setInsulationSecondLayer(unitPrice.getInsulationSecondLayer() *
                (questionnaire.getSlabAreaFull() -
                        (questionnaire.getLengthInsideWall() + questionnaire.getLengthOutsideWall()) * 0.45f));
        price.setInstallationPlate(unitPrice.getInstallationPlate() * questionnaire.getSlabPerimeter());
        return price;
    }
}
