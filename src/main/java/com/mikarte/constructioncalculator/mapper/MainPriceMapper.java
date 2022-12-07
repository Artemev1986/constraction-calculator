package com.mikarte.constructioncalculator.mapper;

import com.mikarte.constructioncalculator.model.KeyMainWorking;
import com.mikarte.constructioncalculator.model.MainPrice;

import java.util.Map;

public class MainPriceMapper {
    public static MainPrice toMainPriceFromMap(Map<KeyMainWorking, Float> mainWorkMap) {
        MainPrice mainPrice = new MainPrice();
        mainPrice.setId(1L);
        mainPrice.setBreakdownOfAxes(mainWorkMap.get(KeyMainWorking.BREAKDOWN_OF_AXES));
        mainPrice.setDevelopmentOfPit(mainWorkMap.get(KeyMainWorking.DEVELOPMENT_OF_PIT));
        mainPrice.setEquippingPillow(mainWorkMap.get(KeyMainWorking.EQUIPPING_PILLOW));
        mainPrice.setLayingWaterproof(mainWorkMap.get(KeyMainWorking.LAYING_WATERPROOF));
        mainPrice.setLayingGeotextile(mainWorkMap.get(KeyMainWorking.LAYING_GEOTEXTILE));
        mainPrice.setInsulationFirstLayer(mainWorkMap.get(KeyMainWorking.INSULATION_FIRST_LAYER));
        mainPrice.setInsulationSecondLayer(mainWorkMap.get(KeyMainWorking.INSULATION_SECOND_LAYER));
        mainPrice.setInstallationPlate(mainWorkMap.get(KeyMainWorking.INSTALLATION_PLATE));
        return mainPrice;
    }
}
