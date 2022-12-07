package com.mikarte.constructioncalculator.mapper;

import com.mikarte.constructioncalculator.model.AdditionalPrice;
import com.mikarte.constructioncalculator.model.KeyAdditionalWorkAndEquipment;

import java.util.Map;

public class AdditionalPriceMapper {

    public static AdditionalPrice toAdditionalPriceFromMap(Map<KeyAdditionalWorkAndEquipment, Float> additionalWorkMap) {
        AdditionalPrice additionalPrice = new AdditionalPrice();
        additionalPrice.setId(1L);
        additionalPrice.setInstallationInsulatedBlindArea(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.INSTALLATION_INSULATED_BLIND_AREA));
        additionalPrice.setInstallationDrainageDitch(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.INSTALLATION_DRAINAGE_DITCH));
        additionalPrice.setInstallationDrainageWells(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.INSTALLATION_DRAINAGE_WELLS));
        additionalPrice.setInstallationRainInlets(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.INSTALLATION_RAIN_INLETS));
        additionalPrice.setInstallationStormSewerPipes(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.INSTALLATION_STORM_SEWER_PIPES));
        additionalPrice.setInstallationSeptic(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.INSTALLATION_SEPTIC));
        additionalPrice.setInstallationCaisson(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.INSTALLATION_CAISSON));
        additionalPrice.setBusinessTrip(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.BUSINESS_TRIP));
        additionalPrice.setTransport(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.TRANSPORT));
        additionalPrice.setBackhoeLoader(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.BACKHOE_LOADER));
        additionalPrice.setConcretePump(additionalWorkMap.get(KeyAdditionalWorkAndEquipment.CONCRETE_PUMP));
        return additionalPrice;
    }
}
