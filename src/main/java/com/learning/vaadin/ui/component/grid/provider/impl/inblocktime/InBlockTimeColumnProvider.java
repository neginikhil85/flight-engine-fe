package com.learning.vaadin.ui.component.grid.provider.impl.inblocktime;

import com.learning.model.grid.InBlockTime;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class InBlockTimeColumnProvider implements ColumnProvider<InBlockTime> {

    @Override
    public Class<InBlockTime> beanType() {
        return InBlockTime.class;
    }

    @Override
    public Map<String, ValueProvider<InBlockTime, ?>> getHeaderAndValueProviders() {
        return  new LinkedHashMap<>() {{
            put("Flight No.", InBlockTime::getFlightNumber);
            put("Date Of Origin", InBlockTime::getDateOfOrigin);
            put("Start Station", InBlockTime::getStartStation);
            put("End Station", InBlockTime::getEndStation);
            put("Scheduled Start Time", InBlockTime::getScheduledStartTime);
            put("In Block Time", InBlockTime::getCurrentInBlock);
            put("Event Received On", safeValueProvider(inBlockTime -> CommonUtils.getFormattedDate(inBlockTime.getEventReceivedOn())));
        }};
    }
}
