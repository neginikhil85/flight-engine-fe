package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.model.grid.Diversion;
import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.vaadin.flow.function.SerializablePredicate;

public class DiversionDataGridFilters implements GridFilters<Diversion> {
    @Override
    public SerializablePredicate<Diversion> getFilters(String searchTerm) {
        return diversion -> {
            if (searchTerm.isBlank())
                return true;

            return matchesTerm(String.valueOf(diversion.getFlightNumber()), searchTerm) ||
                    matchesTerm(String.valueOf(diversion.getDateOfOrigin()), searchTerm) ||
                    matchesTerm(String.valueOf(diversion.getStartStation()), searchTerm) ||
                    matchesTerm(String.valueOf(diversion.getEndStation()), searchTerm) ||
                    matchesTerm(String.valueOf(diversion.getScheduledStartTime()), searchTerm) ||
                    matchesTerm(String.valueOf(diversion.getFlightStatus()), searchTerm) ||
                    matchesTerm(String.valueOf(diversion.getEffectiveArrivalStation()), searchTerm) ||
                    matchesTerm(String.valueOf(diversion.getDiversionCode()), searchTerm) ||
                    matchesTerm(String.valueOf(diversion.getEstimatedInBlock()), searchTerm) ||
                    matchesTerm(String.valueOf(diversion.getRegistration()), searchTerm);
        };
    }


}

