package com.learning.vaadin.ui.component.grid.provider.impl.actualtime;

import com.learning.model.grid.ActualTime;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ActualTimeColumnProvider implements ColumnProvider<ActualTime> {
    @Override
    public Class<ActualTime> beanType() {
        return ActualTime.class;
    }

    @Override
    public Map<String, ValueProvider<ActualTime, ?>> getHeaderAndValueProviders() {
        return new LinkedHashMap<>() {{
            put("Flight No.", ActualTime::getFlightNumber);
            put("Date Of Origin", ActualTime::getDateOfOrigin);
            put("Start Station", ActualTime::getStartStation);
            put("End Station", ActualTime::getEndStation);
            put("Schedule Start Time", ActualTime::getScheduledStartTime);
            put("In Block Time",safeValueProvider(actualTime -> actualTime.getInBlockTime().getCurrentInBlock()));
            put("Off Block Time",safeValueProvider(actualTime -> actualTime.getOffBlockTime().getCurrentOffBlock()));
            put("Take Off Time",safeValueProvider(actualTime -> actualTime.getTakeOffTime().getCurrentTakeOffTime()));
            put("Landing Time",safeValueProvider(actualTime -> actualTime.getLandingTime().getCurrentLandingTime()));
        }};
    }
}
