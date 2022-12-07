package com.mikarte.constructioncalculator.mapper;

import com.mikarte.constructioncalculator.model.*;

import java.time.LocalDateTime;
import java.util.Map;

public class FullPriceMapper {

    public static FullPrice toFullPriceFromMap(Map<KeyMainWorking, Float> mainWorkMap,
                                               Map<KeyCommunicationWorking, Float> communicationWorkMap,
                                               Map<KeyConcreteWorking, Float> concreteWorkMap,
                                               Map<KeyAdditionalWorkAndEquipment, Float> additionalWorkMap) {

        FullPrice fullPrice = new FullPrice();
        fullPrice.setId(1L);
        fullPrice.setMainPrice(MainPriceMapper.toMainPriceFromMap(mainWorkMap));
        fullPrice.setCommunicationPrice(CommunicationPriceMapper.toCommunicationPriceFromMap(communicationWorkMap));
        fullPrice.setConcretePrice(ConcretePriceMapper.toConcretePriceFromMap(concreteWorkMap));
        fullPrice.setAdditionalPrice(AdditionalPriceMapper.toAdditionalPriceFromMap(additionalWorkMap));
        fullPrice.setUpdated(LocalDateTime.now());

        return fullPrice;
    }
}
