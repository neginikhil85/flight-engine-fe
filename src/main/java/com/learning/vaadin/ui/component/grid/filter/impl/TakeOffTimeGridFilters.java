package com.learning.vaadin.ui.component.grid.filter.impl;

import com.learning.model.grid.TakeOffTime;
import com.learning.vaadin.ui.component.grid.filter.GridFilters;
import com.vaadin.flow.function.SerializablePredicate;

public class TakeOffTimeGridFilters implements GridFilters<TakeOffTime> {

    @Override
    public SerializablePredicate<TakeOffTime> getFilters(String searchTerm) {
        return null;
    }
}
