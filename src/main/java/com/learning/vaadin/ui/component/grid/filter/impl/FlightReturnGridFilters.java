package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.learning.model.grid.FlightReturn;
import com.vaadin.flow.function.SerializablePredicate;

public class FlightReturnGridFilters implements GridFilters<FlightReturn> {
    @Override
    public SerializablePredicate<FlightReturn> getFilters(String searchTerm) {
        return flightReturn -> {
            if (searchTerm.isBlank())
                return true;

            return matchesTerm(String.valueOf(flightReturn.getFlightNumber()), searchTerm) ||
                    matchesTerm(String.valueOf(flightReturn.getDateOfOrigin()), searchTerm) ||
                    matchesTerm(String.valueOf(flightReturn.getStartStation()), searchTerm) ||
                    matchesTerm(String.valueOf(flightReturn.getEndStation()), searchTerm) ||
                    matchesTerm(String.valueOf(flightReturn.getScheduledStartTime()), searchTerm) ||
                    safeValueMatcher(flightReturn, fr -> fr.getReturnAtom().getReturnNumber(), searchTerm) ||
                    safeValueMatcher(flightReturn, fr -> fr.getReturnAtom().getAircraft(), searchTerm) ||
                    safeValueMatcher(flightReturn, fr -> fr.getReturnAtom().getInBlockFuel(), searchTerm) ||
                    safeValueMatcher(flightReturn, fr -> fr.getReturnAtom().getScheduleStatus(), searchTerm);
        };
    }
    }
