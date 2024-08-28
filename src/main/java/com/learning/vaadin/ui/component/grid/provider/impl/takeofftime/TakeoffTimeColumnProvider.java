package com.learning.vaadin.ui.component.grid.provider.impl.takeofftime;

import com.learning.model.grid.TakeOffTime;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class TakeoffTimeColumnProvider implements ColumnProvider<TakeOffTime> {

    @Override
    public Class<TakeOffTime> beanType() {
        return TakeOffTime.class;
    }

    @Override
    public Map<String, ValueProvider<TakeOffTime, ?>> getHeaderAndValueProviders() {
        return  new LinkedHashMap<>() {{
            put("Flight No.", TakeOffTime::getFlightNumber);
            put("Date Of Origin", TakeOffTime::getDateOfOrigin);
            put("Start Station", TakeOffTime::getStartStation);
            put("End Station", TakeOffTime::getEndStation);
            put("Scheduled Start Time", TakeOffTime::getScheduledStartTime);
            put("Event Received On", safeValueProvider(takeOffTime -> CommonUtils.getFormattedDate(takeOffTime.getEventReceivedOn())));
        }};
    }
}
