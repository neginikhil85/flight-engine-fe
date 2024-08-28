package com.learning.vaadin.ui.component.grid.provider.impl.offblocktime;

import com.learning.model.grid.OffBlockTime;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class OffBlockTimeColumnProvider implements ColumnProvider<OffBlockTime> {

    @Override
    public Class<OffBlockTime> beanType() {
        return OffBlockTime.class;
    }

    @Override
    public Map<String, ValueProvider<OffBlockTime, ?>> getHeaderAndValueProviders() {
        return  new LinkedHashMap<>() {{
            put("Flight No.", OffBlockTime::getFlightNumber);
            put("Date Of Origin", OffBlockTime::getDateOfOrigin);
            put("Start Station", OffBlockTime::getStartStation);
            put("End Station", OffBlockTime::getEndStation);
            put("Scheduled Start Time", OffBlockTime::getScheduledStartTime);
            put("Off Block Time", OffBlockTime::getCurrentOffBlock);
            put("Event Received On", safeValueProvider(offBlockTime -> CommonUtils.getFormattedDate(offBlockTime.getEventReceivedOn())));
        }};
    }
}
