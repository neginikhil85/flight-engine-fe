package com.learning.vaadin.ui.component.grid.provider.impl.landingtime;

import com.learning.model.grid.LandingTime;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class LandingTimeColumnProvider implements ColumnProvider<LandingTime> {

    @Override
    public Class<LandingTime> beanType() {
        return LandingTime.class;
    }

    @Override
    public Map<String, ValueProvider<LandingTime, ?>> getHeaderAndValueProviders() {
        return  new LinkedHashMap<>() {{
            put("Flight No.", LandingTime::getFlightNumber);
            put("Date Of Origin", LandingTime::getDateOfOrigin);
            put("Start Station", LandingTime::getStartStation);
            put("End Station", LandingTime::getEndStation);
            put("Scheduled Start Time", LandingTime::getScheduledStartTime);
            put("Landing Time", LandingTime::getCurrentLandingTime);
            put("Event Received On", safeValueProvider(landingTime -> CommonUtils.getFormattedDate(landingTime.getEventReceivedOn())));
        }};
    }
}
