package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.model.grid.InBlockTime;
import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.vaadin.flow.function.SerializablePredicate;

public class InBlockTimeGridFilters implements GridFilters<InBlockTime> {

    @Override
    public SerializablePredicate<InBlockTime> getFilters(String searchTerm) {
        return null;
    }
}
