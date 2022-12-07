package com.mikarte.constructioncalculator.mapper;

import com.mikarte.constructioncalculator.model.CommunicationPrice;
import com.mikarte.constructioncalculator.model.KeyCommunicationWorking;

import java.util.Map;

public class CommunicationPriceMapper {

    public static CommunicationPrice toCommunicationPriceFromMap(
            Map<KeyCommunicationWorking, Float> communicationWorkMap) {
        CommunicationPrice communicationPrice = new CommunicationPrice();
        communicationPrice.setId(1L);
        communicationPrice.setInstallationHeatFloorPipes(communicationWorkMap.get(KeyCommunicationWorking.INSTALLATION_HEAT_FLOOR_PIPES));
        communicationPrice.setInstallationSewerPipes(communicationWorkMap.get(KeyCommunicationWorking.INSTALLATION_SEWER_PIPES));
        communicationPrice.setInstallationSourceWaterPipes(communicationWorkMap.get(KeyCommunicationWorking.INSTALLATION_SOURCE_WATER_PIPES));
        communicationPrice.setInstallationHeatingPipes(communicationWorkMap.get(KeyCommunicationWorking.INSTALLATION_HEATING_PIPES));
        communicationPrice.setCableLaying(communicationWorkMap.get(KeyCommunicationWorking.CABLE_LAYING));
        communicationPrice.setInstallationBackupEmbeds(communicationWorkMap.get(KeyCommunicationWorking.INSTALLATION_BACKUP_EMBEDS));
        communicationPrice.setInstallationWaterEmbeds(communicationWorkMap.get(KeyCommunicationWorking.INSTALLATION_WATER_EMBEDS));
        communicationPrice.setInstallationGround(communicationWorkMap.get(KeyCommunicationWorking.INSTALLATION_GROUND));
        communicationPrice.setManifoldInstallation(communicationWorkMap.get(KeyCommunicationWorking.MANIFOLD_INSTALLATION));
        return communicationPrice;
    }
}
