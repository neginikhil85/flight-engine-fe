package com.learning.vaadin.ui.component.grid.provider.impl.estimatedtime;


import com.learning.model.grid.EstimatedTime;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class EstimatedTimeColumnProvider implements ColumnProvider<EstimatedTime> {

    @Override
    public Class<EstimatedTime> beanType() {
        return EstimatedTime.class;
    }

    @Override
    public Map<String, ValueProvider<EstimatedTime, ?>> getHeaderAndValueProviders() {
        return new LinkedHashMap<>() {{
            put("Flight No.", EstimatedTime::getFlightNumber);
            put("Date Of Origin", EstimatedTime::getDateOfOrigin);
            put("Start Station", EstimatedTime::getStartStation);
            put("End Station", EstimatedTime::getEndStation);
            put("Scheduled Start Time", EstimatedTime::getScheduledStartTime);
            put("In Block Time",safeValueProvider(estimatedTime -> estimatedTime.getInBlockTime().getCurrentInBlock()));
            put("Off Block Time",safeValueProvider(estimatedTime -> estimatedTime.getOffBlockTime().getCurrentOffBlock()));
            put("Take Off Time",safeValueProvider(estimatedTime -> estimatedTime.getTakeOffTime().getCurrentTakeOffTime()));
            put("Landing Time",safeValueProvider(estimatedTime -> estimatedTime.getLandingTime().getCurrentLandingTime()));
            put("Event Received On", delayData -> CommonUtils.getFormattedDate(delayData.getEventReceivedOn()));
        }};
    }
}

