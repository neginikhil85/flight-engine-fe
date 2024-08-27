package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.learning.model.grid.FlightDelete;
import com.vaadin.flow.function.SerializablePredicate;

public class FlightDeleteDataGridFilters implements GridFilters<FlightDelete> {
    @Override
    public SerializablePredicate<FlightDelete> getFilters(String searchTerm) {
        return flightDelete -> {
            if (searchTerm.isBlank())
                return true;

            return matchesTerm(String.valueOf(flightDelete.getFlightNumber()), searchTerm) ||
                    matchesTerm(String.valueOf(flightDelete.getDateOfOrigin()), searchTerm) ||
                    matchesTerm(String.valueOf(flightDelete.getStartStation()), searchTerm) ||
                    matchesTerm(String.valueOf(flightDelete.getEndStation()), searchTerm) ||
                    matchesTerm(String.valueOf(flightDelete.getScheduledStartTime()), searchTerm) ||
                    matchesTerm(String.valueOf(flightDelete.getOperationalStatus()), searchTerm);
        };
    }
}
