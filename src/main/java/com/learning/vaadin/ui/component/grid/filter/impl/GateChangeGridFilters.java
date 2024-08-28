package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.learning.model.grid.GateChange;
import com.vaadin.flow.function.SerializablePredicate;

public class GateChangeGridFilters implements GridFilters<GateChange> {
    @Override
    public SerializablePredicate<GateChange> getFilters(String searchTerm) {
        return gateChange -> {
            if (searchTerm.isBlank())
                return true;

            return matchesTerm(String.valueOf(gateChange.getFlightNumber()), searchTerm) ||
                    matchesTerm(String.valueOf(gateChange.getDateOfOrigin()), searchTerm) ||
                    matchesTerm(String.valueOf(gateChange.getStartStation()), searchTerm) ||
                    matchesTerm(String.valueOf(gateChange.getEndStation()), searchTerm) ||
                    matchesTerm(String.valueOf(gateChange.getScheduledStartTime()), searchTerm) ||
                    safeValueMatcher(gateChange, gc -> gc.getCurrent().getStartTerminal(), searchTerm) ||
                    safeValueMatcher(gateChange, gc -> gc.getCurrent().getEndTerminal(), searchTerm) ||
                    safeValueMatcher(gateChange, gc -> gc.getCurrent().getStartGate(), searchTerm) ||
                    safeValueMatcher(gateChange, gc -> gc.getCurrent().getEndGate(), searchTerm) ||
                    safeValueMatcher(gateChange, gc -> gc.getCurrent().getStartStand(), searchTerm) ||
                    safeValueMatcher(gateChange, gc -> gc.getCurrent().getEndStand(), searchTerm);
        };
    }
}

