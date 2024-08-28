package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.learning.model.grid.Equipment;
import com.vaadin.flow.function.SerializablePredicate;

public class EquipmentGridFilters implements GridFilters<Equipment> {
    @Override
    public SerializablePredicate<Equipment> getFilters(String searchTerm) {
        return equipment -> {
            if (searchTerm.isBlank())
                return true;

            return matchesTerm(String.valueOf(equipment.getFlightNumber()), searchTerm) ||
                    matchesTerm(String.valueOf(equipment.getDateOfOrigin()), searchTerm) ||
                    matchesTerm(String.valueOf(equipment.getStartStation()), searchTerm) ||
                    matchesTerm(String.valueOf(equipment.getEndStation()), searchTerm) ||
                    matchesTerm(String.valueOf(equipment.getScheduledStartTime()), searchTerm);
        };
    }
}
