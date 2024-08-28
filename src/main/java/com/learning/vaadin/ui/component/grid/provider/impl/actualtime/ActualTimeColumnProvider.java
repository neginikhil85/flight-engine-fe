package com.learning.vaadin.ui.component.grid.provider.impl.actualtime;

import com.learning.model.grid.ActualTimes;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ActualTimeColumnProvider implements ColumnProvider<ActualTimes> {
    @Override
    public Class<ActualTimes> beanType() {
        return ActualTimes.class;
    }

    @Override
    public Map<String, ValueProvider<ActualTimes, ?>> getHeaderAndValueProviders() {
        return new LinkedHashMap<>() {{
            put("Flight No.", ActualTimes::getFlightNumber);
            put("Date Of Origin", ActualTimes::getDateOfOrigin);
            put("Start Station", ActualTimes::getStartStation);
            put("End Station", ActualTimes::getEndStation);
            put("Schedule Start Time", ActualTimes::getScheduledStartTime);
            put("In Block Time",safeValueProvider(actualTimes -> actualTimes.getInBlockTime().getCurrentInBlock()));
            put("Off Block Time",safeValueProvider(actualTimes -> actualTimes.getOffBlockTime().getCurrentOffBlock()));
            put("Take Off Time",safeValueProvider(actualTimes -> actualTimes.getTakeOffTime().getCurrentTakeOffTime()));
            put("Landing Time",safeValueProvider(actualTimes -> actualTimes.getLandingTime().getCurrentLandingTime()));
        }};
    }
}
