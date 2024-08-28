package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.learning.model.grid.Terminal;
import com.vaadin.flow.function.SerializablePredicate;

public class TerminalGridFilters implements GridFilters<Terminal> {
    @Override
    public SerializablePredicate<Terminal> getFilters(String searchTerm) {
        return terminal -> {
            if (searchTerm.isBlank())
                return true;

            return matchesTerm(String.valueOf(terminal.getFlightNumber()), searchTerm) ||
                    matchesTerm(String.valueOf(terminal.getDateOfOrigin()), searchTerm) ||
                    matchesTerm(String.valueOf(terminal.getStartStation()), searchTerm) ||
                    matchesTerm(String.valueOf(terminal.getEndStation()), searchTerm) ||
                    matchesTerm(String.valueOf(terminal.getScheduledStartTime()), searchTerm) ||
                    safeValueMatcher(terminal, t -> t.getCurrent().getStartTerminal(), searchTerm) ||
                    safeValueMatcher(terminal, t -> t.getCurrent().getEndTerminal(), searchTerm) ||
                    safeValueMatcher(terminal, t -> t.getCurrent().getStartGate(), searchTerm) ||
                    safeValueMatcher(terminal, t -> t.getCurrent().getEndGate(), searchTerm) ||
                    safeValueMatcher(terminal, t -> t.getCurrent().getStartStand(), searchTerm) ||
                    safeValueMatcher(terminal, t -> t.getCurrent().getEndStand(), searchTerm);
        };
    }
}

