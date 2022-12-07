package com.mikarte.constructioncalculator.service;

import com.mikarte.constructioncalculator.model.*;

public class Calculator {

    public static float sumMainWorking(MainPrice unitPrice, Questionnaire questionnaire) {
        MainPrice price = new MainPrice();
        price.setBreakdownOfAxes(unitPrice.getBreakdownOfAxes() * questionnaire.getNumberOfAxes());
        price.setDevelopmentOfPit(unitPrice.getDevelopmentOfPit() * 1);
        price.setEquippingPillow(unitPrice.getEquippingPillow() * questionnaire.getPitArea() *
                questionnaire.getSandBedThickness());
        price.setLayingWaterproof(unitPrice.getLayingWaterproof() * questionnaire.getPitArea() * 1.3f);
        price.setLayingGeotextile(unitPrice.getLayingGeotextile() * questionnaire.getPitArea() * 1.3f);
        price.setInsulationFirstLayer(unitPrice.getInsulationFirstLayer() * questionnaire.getSlabAreaFull());
        price.setInsulationSecondLayer(unitPrice.getInsulationSecondLayer() *
                (questionnaire.getSlabAreaFull() -
                        (questionnaire.getLengthInsideWall() + questionnaire.getLengthOutsideWall()) * 0.45f));
        price.setInstallationPlate(unitPrice.getInstallationPlate() * questionnaire.getSlabPerimeter());

        return price.getSum();
    }

    public static float sumCommunicationWorking(CommunicationPrice unitPrice, Questionnaire questionnaire) {
        CommunicationPrice price = new CommunicationPrice();
        price.setInstallationHeatFloorPipes(unitPrice.getInstallationHeatFloorPipes() * questionnaire.getSlabAreaHeated());
        price.setInstallationSewerPipes(unitPrice.getInstallationSewerPipes() * 50);
        price.setInstallationSourceWaterPipes(unitPrice.getInstallationSourceWaterPipes() * 250);
        price.setInstallationHeatingPipes(unitPrice.getInstallationHeatingPipes() * 0);
        price.setCableLaying(unitPrice.getCableLaying() * 0);
        price.setInstallationBackupEmbeds(unitPrice.getInstallationBackupEmbeds() * 2);
        price.setInstallationWaterEmbeds(unitPrice.getInstallationWaterEmbeds() * 20);
        price.setInstallationGround(unitPrice.getInstallationGround() * 1);
        price.setManifoldInstallation(unitPrice.getManifoldInstallation() * 1);

        return price.getSum();
    }

    public static float sumConcreteWorking(ConcretePrice unitPrice, Questionnaire questionnaire) {
        ConcretePrice price = new ConcretePrice();
        price.setFormworkAssemblyDisassembly(unitPrice.getFormworkAssemblyDisassembly() * questionnaire.getSlabPerimeter());
        price.setInstallationFormworkSteps(unitPrice.getInstallationFormworkSteps() * 4);
        price.setRibReinforcement(unitPrice.getRibReinforcement() *
                (questionnaire.getLengthOutsideWall() + questionnaire.getLengthInsideWall()));
        price.setSlabReinforcement(unitPrice.getSlabReinforcement() *
                (questionnaire.getSlabAreaFull() + questionnaire.getLengthInsideWall() + questionnaire.getLengthOutsideWall()));
        price.setConcreting(unitPrice.getConcreting() * (questionnaire.getRibVolume() + questionnaire.getSlabAreaFull() * 0.11f));
        price.setPolesForTerrace(unitPrice.getPolesForTerrace() * 0);
        price.setConcreteGrinding(unitPrice.getConcreteGrinding() * questionnaire.getSlabAreaFull());

        return price.getSum();
    }

    public static float sumAdditionalWorking(AdditionalPrice unitPrice, Questionnaire questionnaire) {
        AdditionalPrice price = new AdditionalPrice();
        price.setInstallationInsulatedBlindArea(unitPrice.getInstallationInsulatedBlindArea() * questionnaire.getBlindArea());
        price.setInstallationDrainageDitch(unitPrice.getInstallationDrainageDitch() * 0);
        price.setInstallationDrainageWells(unitPrice.getInstallationDrainageWells() * 0);
        price.setInstallationRainInlets(unitPrice.getInstallationRainInlets() * 0);
        price.setInstallationStormSewerPipes(unitPrice.getInstallationStormSewerPipes() * 0);
        price.setInstallationSeptic(unitPrice.getInstallationSeptic() * 0);
        price.setInstallationCaisson(unitPrice.getInstallationCaisson() * 0);
        price.setBusinessTrip(unitPrice.getBusinessTrip() * 0);
        price.setTransport(unitPrice.getTransport() * 1);
        price.setBackhoeLoader(unitPrice.getBackhoeLoader() * 1);
        price.setConcretePump(unitPrice.getConcretePump() * 1);

        return price.getSum();
    }

    public static float totalSumWorking(MainPrice mainPrice,
                                 CommunicationPrice communicationPrice,
                                 ConcretePrice concretePrice,
                                 AdditionalPrice additionalPrice,
                                 Questionnaire questionnaire) {
        float sum = 0;
        sum += sumMainWorking(mainPrice, questionnaire);
        sum += sumCommunicationWorking(communicationPrice, questionnaire);
        sum += sumConcreteWorking(concretePrice, questionnaire);
        sum += sumAdditionalWorking(additionalPrice, questionnaire);
        return sum;
    }
}
