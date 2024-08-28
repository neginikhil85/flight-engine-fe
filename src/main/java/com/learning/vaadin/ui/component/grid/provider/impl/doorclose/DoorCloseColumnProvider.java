package com.learning.vaadin.ui.component.grid.provider.impl.doorclose;


import com.learning.model.grid.DoorClose;
import com.learning.util.CommonUtils;
import com.learning.vaadin.ui.component.grid.provider.ColumnProvider;
import com.vaadin.flow.function.ValueProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DoorCloseColumnProvider implements ColumnProvider<DoorClose> {

    @Override
    public Class<DoorClose> beanType() {
        return DoorClose.class;
    }

    @Override
    public Map<String, ValueProvider<DoorClose, ?>> getHeaderAndValueProviders() {
        return new LinkedHashMap<>() {{
            put("Flight No.", DoorClose::getFlightNumber);
            put("Date Of Origin", DoorClose::getDateOfOrigin);
            put("Start Station", DoorClose::getStartStation);
            put("End Station", DoorClose::getEndStation);
            put("Schedule Start Time", DoorClose::getScheduledStartTime);
            put("Door close Time", safeValueProvider(doorClose -> doorClose.getCurrentActualTimes().getDoorClose()));
            put("In Block Time", safeValueProvider(doorClose -> doorClose.getCurrentActualTimes().getInBlock()));
            put("Off Block Time", safeValueProvider(doorClose -> doorClose.getCurrentActualTimes().getOffBlock()));
            put("Take Off Time", safeValueProvider(doorClose -> doorClose.getCurrentActualTimes().getTakeoffTime()));
            put("Landing Time", safeValueProvider(doorClose -> doorClose.getCurrentActualTimes().getLandingTime()));
            put("Event Received On", safeValueProvider(doorClose -> CommonUtils.getFormattedDate(doorClose.getEventReceivedOn())));
        }};
    }
}
