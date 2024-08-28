package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.learning.model.grid.OperationStatus;
import com.vaadin.flow.function.SerializablePredicate;

public class OperationStatusGridFilters implements GridFilters<OperationStatus> {
    @Override
    public SerializablePredicate<OperationStatus> getFilters(String searchTerm) {
        return operationStatus -> {
            if (searchTerm.isBlank())
                return true;

            return matchesTerm(String.valueOf(operationStatus.getFlightNumber()), searchTerm) ||
                    matchesTerm(String.valueOf(operationStatus.getDateOfOrigin()), searchTerm) ||
                    matchesTerm(String.valueOf(operationStatus.getStartStation()), searchTerm) ||
                    matchesTerm(String.valueOf(operationStatus.getEndStation()), searchTerm) ||
                    matchesTerm(String.valueOf(operationStatus.getScheduledStartTime()), searchTerm) ||
                    matchesTerm(String.valueOf(operationStatus.getCurrentOperationalStatus()), searchTerm);
        };
    }
}

