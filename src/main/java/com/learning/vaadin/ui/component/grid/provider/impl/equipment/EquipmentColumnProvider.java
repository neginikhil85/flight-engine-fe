package com.learning.vaadin.ui.component.grid.provider.impl.equipment;


import com.learning.model.grid.Equipment;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class EquipmentColumnProvider implements ColumnProvider<Equipment> {

    @Override
    public Class<Equipment> beanType() {
        return Equipment.class;
    }

    @Override
    public Map<String, ValueProvider<Equipment, ?>> getHeaderAndValueProviders() {
        return new LinkedHashMap<>() {{
            put("Flight No.", Equipment::getFlightNumber);
            put("Date Of Origin", Equipment::getDateOfOrigin);
            put("Start Station", Equipment::getStartStation);
            put("End Station", Equipment::getEndStation);
            put("Scheduled Start Time", Equipment::getScheduledStartTime);
            put("Planned Aircraft Type", safeValueProvider(equipment -> equipment.getCurrent().getPlannedAircraftType()));
            put("Aircraft Registration", safeValueProvider(equipment -> equipment.getCurrent().getAircraft().getRegistration()));
            put("Aircraft Type", safeValueProvider(equipment -> equipment.getCurrent().getAircraft().getType()));
            put("Aircraft Configuration", safeValueProvider(equipment -> equipment.getCurrent().getAircraftConfiguration()));
            put("Assigned Aircraft Configuration", safeValueProvider(equipment -> equipment.getCurrent().getAssignedAircraftConfiguration()));
            put("Event Received On", delayData -> CommonUtils.getFormattedDate(delayData.getEventReceivedOn()));
        }};
    }
}
