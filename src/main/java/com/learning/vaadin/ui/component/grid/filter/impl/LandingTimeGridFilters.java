package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.model.grid.LandingTime;
import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.vaadin.flow.function.SerializablePredicate;

public class LandingTimeGridFilters implements GridFilters<LandingTime> {

    @Override
    public SerializablePredicate<LandingTime> getFilters(String searchTerm) {
        return null;
    }
}
