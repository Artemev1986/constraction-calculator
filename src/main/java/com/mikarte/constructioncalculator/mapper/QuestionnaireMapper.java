package com.mikarte.constructioncalculator.mapper;

import com.mikarte.constructioncalculator.model.Questionnaire;
import com.mikarte.constructioncalculator.model.User;
import com.mikarte.constructioncalculator.model.InputStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class QuestionnaireMapper {

    public static Questionnaire toQuestionnaireFromMap(Map<InputStatus, Float> data, User customer) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setCustomer(customer);
        questionnaire.setSlabPerimeter(data.get(InputStatus.SLAB_PERIMETER));
        questionnaire.setSlabAreaFull(data.get(InputStatus.SLAB_AREA_FULL));
        questionnaire.setSlabAreaBase(data.get(InputStatus.SLAB_AREA_BASE));
        questionnaire.setSlabAreaHeated(data.get(InputStatus.SLAB_AREA_HEATED));
        questionnaire.setNumberOfAxes(data.get(InputStatus.NUMBER_OF_AXES));
        questionnaire.setRouteLength(data.get(InputStatus.ROUTE_LENGTH));
        questionnaire.setSewerRisers(data.get(InputStatus.SEWER_RISERS));
        questionnaire.setDrawOffPoints(data.get(InputStatus.DRAW_OFF_POINTS));
        questionnaire.setCableEntry(data.get(InputStatus.CABLE_ENTRY));
        questionnaire.setGround(data.get(InputStatus.GROUND));
        questionnaire.setLengthOutsideWall(data.get(InputStatus.SLAB_PERIMETER));
        questionnaire.setLengthInsideWall(data.get(InputStatus.LENGTH_INSIDE_WALL));
        questionnaire.setPlateRibWidth(data.get(InputStatus.PLATE_RIB_WIDTH));
        questionnaire.setRibVolume((data.get(InputStatus.SLAB_PERIMETER) + data.get(InputStatus.LENGTH_INSIDE_WALL)) *
                data.get(InputStatus.PLATE_RIB_WIDTH) * 0.1f);
        questionnaire.setPitArea(data.get(InputStatus.SLAB_PERIMETER) + data.get(InputStatus.SLAB_AREA_FULL) + 10);
        questionnaire.setPitDepth(data.get(InputStatus.PIT_DEPTH));
        questionnaire.setBlindArea(data.get(InputStatus.SLAB_PERIMETER) + 4);
        questionnaire.setInsulationThickness(data.get(InputStatus.INSULATION_THICKNESS));
        questionnaire.setSandBedThickness(data.get(InputStatus.SAND_BED_THICKNESS));
        questionnaire.setCreatedOn(LocalDateTime.now());
        return questionnaire;
    }
}
