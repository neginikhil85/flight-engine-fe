package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.learning.model.grid.ActualTime;
import com.vaadin.flow.function.SerializablePredicate;

public class ActualTimeDataGridFilters implements GridFilters<ActualTime> {
    @Override
    public SerializablePredicate<ActualTime> getFilters(String searchTerm) {
        return actualTime -> {
            if (searchTerm.isBlank())
                return true;

            return matchesTerm(String.valueOf(actualTime.getFlightNumber()), searchTerm) ||
                    matchesTerm(String.valueOf(actualTime.getDateOfOrigin()), searchTerm) ||
                    matchesTerm(String.valueOf(actualTime.getStartStation()), searchTerm) ||
                    matchesTerm(String.valueOf(actualTime.getEndStation()), searchTerm) ||
                    matchesTerm(String.valueOf(actualTime.getScheduledStartTime()), searchTerm);
        };
    }
}

