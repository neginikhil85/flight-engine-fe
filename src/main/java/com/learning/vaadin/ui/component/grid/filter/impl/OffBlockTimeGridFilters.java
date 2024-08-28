package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.model.grid.OffBlockTime;
import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.vaadin.flow.function.SerializablePredicate;

public class OffBlockTimeGridFilters implements GridFilters<OffBlockTime> {

    @Override
    public SerializablePredicate<OffBlockTime> getFilters(String searchTerm) {
        return null;
    }
}
