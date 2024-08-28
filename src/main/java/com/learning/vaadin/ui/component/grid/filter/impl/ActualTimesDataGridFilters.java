package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.model.grid.ActualTimes;
import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.vaadin.flow.function.SerializablePredicate;

public class ActualTimesDataGridFilters implements GridFilters<ActualTimes> {
    @Override
    public SerializablePredicate<ActualTimes> getFilters(String searchTerm) {
        return actualTimes -> {
            if (searchTerm.isBlank())
                return true;

            return matchesTerm(String.valueOf(actualTimes.getFlightNumber()), searchTerm) ||
                    matchesTerm(String.valueOf(actualTimes.getDateOfOrigin()), searchTerm) ||
                    matchesTerm(String.valueOf(actualTimes.getStartStation()), searchTerm) ||
                    matchesTerm(String.valueOf(actualTimes.getEndStation()), searchTerm) ||
                    matchesTerm(String.valueOf(actualTimes.getScheduledStartTime()), searchTerm);
        };
    }
}

