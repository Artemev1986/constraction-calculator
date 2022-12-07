package com.mikarte.constructioncalculator.mapper;

import com.mikarte.constructioncalculator.model.ConcretePrice;
import com.mikarte.constructioncalculator.model.KeyConcreteWorking;

import java.util.Map;

public class ConcretePriceMapper {

    public static ConcretePrice toConcretePriceFromMap(Map<KeyConcreteWorking, Float> concreteWorkMap) {
        ConcretePrice concretePrice = new ConcretePrice();
        concretePrice.setId(1L);
        concretePrice.setFormworkAssemblyDisassembly(concreteWorkMap.get(KeyConcreteWorking.FORMWORK_ASSEMBLY_DISASSEMBLY));
        concretePrice.setInstallationFormworkSteps(concreteWorkMap.get(KeyConcreteWorking.INSTALLATION_FORMWORK_STEPS));
        concretePrice.setRibReinforcement(concreteWorkMap.get(KeyConcreteWorking.RIB_REINFORCEMENT));
        concretePrice.setSlabReinforcement(concreteWorkMap.get(KeyConcreteWorking.SLAB_REINFORCEMENT));
        concretePrice.setConcreting(concreteWorkMap.get(KeyConcreteWorking.CONCRETING));
        concretePrice.setPolesForTerrace(concreteWorkMap.get(KeyConcreteWorking.POLES_FOR_TERRACE));
        concretePrice.setConcreteGrinding(concreteWorkMap.get(KeyConcreteWorking.CONCRETE_GRINDING));
        return concretePrice;
    }
}
