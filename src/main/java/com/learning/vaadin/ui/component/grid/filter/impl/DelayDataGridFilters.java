package com.learning.vaadin.ui.component.grid.filter.impl;


import com.learning.model.grid.Delay;
import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.vaadin.flow.function.SerializablePredicate;

import java.util.function.Predicate;

public class DelayDataGridFilters implements GridFilters<Delay> {

    @Override
    public SerializablePredicate<Delay> getFilters(String searchTerm) {
        return delayData -> {
            if (searchTerm.isBlank())
                return true;

            return matchesTerm(String.valueOf(delayData.getFlightNumber()), searchTerm) ||
                    matchesTerm(String.valueOf(delayData.getDateOfOrigin()), searchTerm) ||
                    matchesTerm(String.valueOf(delayData.getStartStation()), searchTerm) ||
                    matchesTerm(String.valueOf(delayData.getEndStation()), searchTerm) ||
                    matchesTerm(String.valueOf(delayData.getScheduledStartTime()), searchTerm) ||
                    matchesTerm(delayData.getDelayLimit(), searchTerm) ||
                    matchesTerm(delayData.getRemarks(), searchTerm) ||
                    matchesTerm(delayData.getTotal(), searchTerm) ||
                    anyMatches(delayData, e -> e.getReason().equals(searchTerm)) ||
                    anyMatches(delayData, e -> e.getDelayNumber().equals(searchTerm)) ||
                    anyMatches(delayData, e -> e.getRemark().equals(searchTerm));
        };
    }

    private static boolean anyMatches(Delay delay, Predicate<Delay.DelayItem> predicate) {
        return delay.getDelays().stream().anyMatch(predicate);
    }
}
